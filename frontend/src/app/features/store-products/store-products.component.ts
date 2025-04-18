import { Component, computed, effect, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {GameStatusBarComponent} from "@shared/game-status-bar/game-status-bar.component";
import {ServicePopupComponent} from '@shared/service-popup/service-popup.component';
import { ProductsByCityService } from '@core/services/products-by-city.service';
import { ProductsByCity } from '@shared/models/products-by-city';
import { CurrentGameService } from '@core/services/current-game.service';
import { InventoryPanelComponent } from "../../shared/inventory-panel/inventory-panel.component";
import { ProductMapper } from '@shared/models/product.mapper';
import { ProductStorePopupComponent } from '@shared/product-store-popup/product-store-popup.component';
import { ProductForStore } from '@shared/models/product-for-store';
import { ProductDisplayItem } from '@shared/models/product-display-item';

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

  productsByCity = signal<ProductsByCity[]>([]);

  productItems = computed<ProductDisplayItem[]>(() =>
    ProductMapper.fromCityList(this.productsByCity())
  );
  
  selectedProduct: ProductForStore  | null = null;
  
  onItemSelected(item: ProductDisplayItem): void {
    this.selectedProduct = item as ProductForStore;
  }
  
  


  constructor(){
    effect( () => {
      const cityId = this.currentGame.selectedGame()?.game.caravan.currentCity.idCity;
      if(!cityId) return ;

      this.cityService.getProductsByCity(cityId).subscribe({
        next: (res: ProductsByCity[]) => this.productsByCity.set(res),
        error: (err: any) => console.error('Error loading products', err)
      });
    });
  }


  onBuy(event: { product: ProductForStore; quantity: number }): void {
    console.log('🛒 Compra realizada:', event);
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



  
}
