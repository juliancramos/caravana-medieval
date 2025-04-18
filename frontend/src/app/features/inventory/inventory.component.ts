import { Component, inject, signal, effect, computed } from '@angular/core';
import { Router } from '@angular/router';
import { ProductsByCaravan } from '@shared/models/products-by-caravan';
import { CurrentGameService } from '@core/services/current-game.service';
import { ProductsByCaravanService } from '@core/services/products-by-caravan.service';
import { ServicePopupComponent } from '@shared/service-popup/service-popup.component';
import { CommonModule } from '@angular/common';
import { GameStatusBarComponent } from '@shared/game-status-bar/game-status-bar.component';
import { InventoryPanelComponent } from '../../shared/inventory-panel/inventory-panel.component';
import { ProductWithQuantity } from '@shared/models/product-with-quantity';
import { ProductMapper } from '@shared/models/product.mapper';
import { ProductPopupComponent } from '@shared/product-popup/product-popup.component';

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
  //Mapea directamente de ProductsByCaravan a ProductWithQuantity
  productItems = computed<ProductWithQuantity[]>(() =>
    ProductMapper.fromCaravanList(this.productsByCaravan())
  );

  selectedService: any = null;

  constructor() {
    effect(() => {
      const caravanId = this.currentGame.selectedGame()?.game.caravan.idCaravan;
      if (!caravanId) return;

      this.productService.getProductsByCaravan(caravanId).subscribe({
        next: (res: ProductsByCaravan[]) => this.productsByCaravan.set(res),
        error: (err: any) => console.error('Error loading products', err)
      });
    });
  }

  selectedProduct: ProductWithQuantity | null = null;

  onItemSelected(item: ProductWithQuantity): void {
    this.selectedProduct = item;
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
