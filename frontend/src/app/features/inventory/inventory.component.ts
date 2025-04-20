import { Component, inject, signal, effect, computed } from '@angular/core';
import { Router } from '@angular/router';
import { ProductsByCaravan } from '@shared/models/products-by-caravan';
import { CurrentGameService } from '@core/services/current-game.service';
import { ProductsByCaravanService } from '@core/services/products-by-caravan.service';
import { ServicePopupComponent } from '@shared/service-popup/service-popup.component';
import { CommonModule } from '@angular/common';
import { GameStatusBarComponent } from '@shared/game-status-bar/game-status-bar.component';
import { InventoryPanelComponent } from '../../shared/inventory-panel/inventory-panel.component';
import { ProductMapper } from '@shared/models/product.mapper';
import { ProductPopupComponent } from '@shared/product-popup/product-popup.component';
import { StoreService } from '@core/services/store.service';
import { SellProductDTO } from '@shared/models/sell-product';
import { ProductsByCityService } from '@core/services/products-by-city.service';
import { ProductsByCity } from '@shared/models/products-by-city';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss'],
  imports: [
    ServicePopupComponent,
    CommonModule,
    GameStatusBarComponent,
    InventoryPanelComponent,
    ProductPopupComponent
  ]
})
export class InventoryComponent {
  private router = inject(Router);
  private currentGame = inject(CurrentGameService);
  private productService = inject(ProductsByCaravanService);

  productsByCaravan = signal<ProductsByCaravan[]>([]);
  productItems = computed<SellProductDTO[]>(() =>
    ProductMapper.fromCaravanWithPrices(this.productsByCaravan(), this.cityProducts())
  );

  private cityProductService = inject(ProductsByCityService);
  cityProducts = signal<ProductsByCity[]>([])

  

  storeService = inject(StoreService);

  constructor() {
    effect(() => {
      const caravanId = this.currentGame.selectedGame()?.game.caravan.idCaravan;
      const cityId = this.currentGame.selectedGame()?.game.caravan.currentCity.idCity;
  
      if (!caravanId || !cityId) return;
  
      this.productService.getProductsByCaravan(caravanId).subscribe({
        next: res => {
          //ordena según el id del producto
          const sorted = [...res].sort((a, b) => a.product.idProduct - b.product.idProduct);
          this.productsByCaravan.set(sorted);
        },
        error: err => console.error(err)
      });
      
      //Necesario traer los productos por ciudad ya que se necesita el precio
      this.cityProductService.getProductsByCity(cityId).subscribe({
        next: res => this.cityProducts.set(res),
        error: err => console.error(err)
      });
    });
  }
  
  //Para el popup
  selectedProduct: SellProductDTO | null = null;

  onItemSelected(item: SellProductDTO): void {
    this.selectedProduct = item;
  }


  //Vender
  onSell(event: SellProductDTO) {
    const caravanId = this.currentGame.selectedGame()?.game.caravan.idCaravan;
    const cityId = this.currentGame.selectedGame()?.game.caravan.currentCity.idCity;
  
    if (!caravanId || !cityId) return;
  
    const body = {
      productId: event.product.idProduct,
      quantity: event.quantity,
      caravanId,
      cityId,
    };
    
    //Quita popup de producto
    this.selectedProduct = null;
    //popup para tiempo de espera
    this.showFeedback('Procesando...', 'Validando venta...');
  
    this.storeService.saleProduct(body).subscribe({
      next: () => {
        //Actualiza inventario y oro
        this.updateLocalInventory(event.product.idProduct, event.quantity);
        this.currentGame.updateAvailableGold(+event.price * event.quantity);
        this.showFeedback('¡Venta realizada!', 'Has recibido oro por el producto');
      },
      error: () => {
        this.showFeedback('Error en venta', 'No se pudo completar la venta');
      }
    });
  }

  //feedback de estado de la venta
  feedbackVisible = signal(false);
  feedbackTitle = signal('');
  feedbackMessage = signal('');

  private showFeedback(title: string, message: string, durationMs: number = 2000): void {
    this.feedbackTitle.set(title);
    this.feedbackMessage.set(message);
    this.feedbackVisible.set(true);
    setTimeout(() => this.feedbackVisible.set(false), durationMs);
  }

  
  //actualiza el inventario
  private updateLocalInventory(productId: number, quantitySold: number) {
    this.productsByCaravan.update((currentList) =>
      currentList
        .map(item => {
          if (item.product.idProduct === productId) {
            const newQuantity = item.quantity - quantitySold;
            return newQuantity > 0 ? { ...item, quantity: newQuantity } : null;
          }
          return item;
        })
        .filter((item): item is ProductsByCaravan => item !== null)
    );
  }
  
  

  selectedService: any = null;
  exitInventory(): void {
    this.router.navigate(['/resume']);
  }

  showServiceInfo(service: any): void {
    this.selectedService = service;
  }

  closeServiceInfo(): void {
    this.selectedService = null;
  }
}
