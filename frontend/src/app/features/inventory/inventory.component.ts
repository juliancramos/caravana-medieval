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
import { ProductForStore } from '@shared/models/product-for-store';
import { SellProductDTO } from '@shared/models/sell-product';
import { ProductsByCityService } from '@core/services/products-by-city.service';
import { ProductsByCity } from '@shared/models/products-by-city';
import { BaseProductItem } from '@shared/models/base-product-item';

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

  selectedService: any = null;

  storeService = inject(StoreService);

  constructor() {
    effect(() => {
      const caravanId = this.currentGame.selectedGame()?.game.caravan.idCaravan;
      const cityId = this.currentGame.selectedGame()?.game.caravan.currentCity.idCity;
  
      if (!caravanId || !cityId) return;
  
      this.productService.getProductsByCaravan(caravanId).subscribe({
        next: res => this.productsByCaravan.set(res),
        error: err => console.error(err)
      });
  
      this.cityProductService.getProductsByCity(cityId).subscribe({
        next: res => this.cityProducts.set(res),
        error: err => console.error(err)
      });
    });
  }
  

  selectedProduct: SellProductDTO | null = null;

  onItemSelected(item: SellProductDTO): void {
    this.selectedProduct = item;
  }


  //Vender
  onSell(event: { product: BaseProductItem; quantity: number }) {
    const caravanId = this.currentGame.selectedGame()?.game.caravan.idCaravan;
    const cityId = this.currentGame.selectedGame()?.game.caravan.currentCity.idCity;
  
    if (!caravanId || !cityId) return;
  
    const body = {
      productId: event.product.product.idProduct,
      quantity: event.quantity,
      caravanId,
      cityId,
    };
  
    // this.storeService.saleProduct(body).subscribe({
    //   next: () => {
    //     this.updateLocalInventory(event.product.product.idProduct, event.quantity);
    //     this.currentGame.updateAvailableGold(+this.calculateSalePrice(event.product, event.quantity));
    //     this.selectedProduct = null;
    //     // Aquí puedes mostrar un feedback visual tipo "Venta exitosa"
    //   },
    //   error: () => {
    //     // Aquí puedes mostrar feedback de error: "No se pudo vender"
    //   }
    // });
  }

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
