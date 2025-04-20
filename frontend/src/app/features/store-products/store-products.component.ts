import { Component, computed, effect, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { GameStatusBarComponent } from '@shared/game-status-bar/game-status-bar.component';
import { ServicePopupComponent } from '@shared/service-popup/service-popup.component';
import { ProductsByCityService } from '@core/services/products-by-city.service';
import { ProductsByCity } from '@shared/models/products-by-city';
import { CurrentGameService } from '@core/services/current-game.service';
import { InventoryPanelComponent } from '../../shared/inventory-panel/inventory-panel.component';
import { ProductMapper } from '@shared/models/product.mapper';
import { ProductStorePopupComponent } from '@shared/product-store-popup/product-store-popup.component';
import { ProductForStore } from '@shared/models/product-for-store';
import { ProductDisplayItem } from '@shared/models/product-display-item';
import { StoreService } from '@core/services/store.service';

@Component({
  selector: 'app-store-products',
  standalone: true,
  templateUrl: './store-products.component.html',
  styleUrls: ['./store-products.component.scss'],
  imports: [
    CommonModule,
    GameStatusBarComponent,
    ServicePopupComponent,
    InventoryPanelComponent,
    ProductStorePopupComponent,
  ]
})
export class StoreProductsComponent {
  selectedService: any = null;

  private router = inject(Router);
  private currentGame = inject(CurrentGameService);
  private cityService = inject(ProductsByCityService);
  private storeService = inject(StoreService);

  //Lista de productos en el panel
  productsByCity = signal<ProductsByCity[]>([]);
  productItems = computed<ProductDisplayItem[]>(() =>
    ProductMapper.fromCityList(this.productsByCity())
  );
  //Producto seleccionado para desplegar popup
  selectedProduct: ProductForStore | null = null;

  onItemSelected(item: ProductDisplayItem): void {
    this.selectedProduct = item as ProductForStore;
  }

  constructor() {
    //Traer los productos ordenados por id del producto
    effect(() => {
      const cityId = this.currentGame.selectedGame()?.game.caravan.currentCity.idCity;
      if (!cityId) return;

      this.cityService.getProductsByCity(cityId).subscribe({
        next: (res: ProductsByCity[]) => {
          const sorted = [...res].sort((a, b) => a.product.idProduct - b.product.idProduct);
          this.productsByCity.set(sorted);
        },
        error: (err: any) => console.error('Error loading products', err)
      });
    });
  }

  onBuy(event: { product: ProductForStore; quantity: number }) {
    const caravanId = this.currentGame.selectedGame()?.game.caravan.idCaravan;
    const cityId = this.currentGame.selectedGame()?.game.caravan.currentCity.idCity;
    if (!caravanId || !cityId) return;
  
    this.showFeedback('Procesando...', 'Validando compra...');
    //cerrar el producto de inmediato
    this.selectedProduct = null;
    
    //Formato de BuyProductDTO en el back
    const body = {
      productId: event.product.product.idProduct,
      quantity: event.quantity,
      caravanId,
      cityId,
    };
  
    this.storeService.buyProduct(body).subscribe({
      next: () => {
        this.currentGame.updateAvailableGold(-event.product.price * event.quantity);
        this.updateLocalCityInventory(event.product.product.idProduct, event.quantity);
        this.showFeedback('¡Compra realizada!', 'El producto fue agregado al inventario');
      },
      error: () => {
        this.showFeedback('Compra fallida', 'No tienes suficiente oro');
      },
    });
  }
  
  //Actualiza visualmente el inventario para que no se demore tanto visualmente al esperar respuesta del back
  private updateLocalCityInventory(productId: number, quantityBought: number) {
    this.productsByCity.update((currentList) =>
      currentList
        .map(item => {
          if (item.product.idProduct === productId) {
            const newQuantity = item.quantity - quantityBought;
            //Crea copia del objeto para no cambiar directamente
            return newQuantity > 0 ? { ...item, quantity: newQuantity } : null;
          }
          return item;
        })
        .filter((item): item is ProductsByCity => item !== null)
    );
  }

  exitStore(): void {
    this.router.navigate(['/resume']);
  }

  showServiceInfo(service: any): void {
    this.selectedService = service;
  }

  closeServiceInfo(): void {
    this.selectedService = null;
  }

  // Feedback popup: Esos mensajes salen antes mientras se procesa
  feedbackVisible = signal(false);
  feedbackTitle = signal('Procesando...');
  feedbackMessage = signal('Validando compra...');

  private showFeedback(title: string, message: string, durationMs: number = 2000): void {
    this.feedbackTitle.set(title);
    this.feedbackMessage.set(message);
    this.feedbackVisible.set(true);
    setTimeout(() => this.feedbackVisible.set(false), durationMs);
  }

  goToInventory(): void {
    this.router.navigate(['/inventory']);
  }
  


}
