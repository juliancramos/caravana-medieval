import { Component, inject, signal, effect } from '@angular/core';
import { Router } from '@angular/router';
import { ProductsByCaravan } from '@shared/models/products-by-caravan';
import { CurrentGameService } from '@core/services/current-game.service';
import { ProductsByCaravanService } from '@core/services/products-by-caravan.service';
import { ServicePopupComponent } from '@shared/service-popup/service-popup.component';
import { CommonModule } from '@angular/common';
import { GameStatusBarComponent } from '@shared/game-status-bar/game-status-bar.component';

@Component({
  selector: 'app-inventory',
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss'],
  imports: [
    ServicePopupComponent,
    CommonModule,
    GameStatusBarComponent,
  ]
})
export class InventoryComponent {
  private router = inject(Router);
  private currentGame = inject(CurrentGameService);
  private productService = inject(ProductsByCaravanService);

  products = signal<ProductsByCaravan[]>([]);
  selectedCategory = 'all'; 
  selectedService: any = null;

  constructor() {
    effect(() => {
      const caravanId = this.currentGame.selectedGame()?.game.caravan.idCaravan;
      if (!caravanId) return;

      this.productService.getProductsByCaravan(caravanId).subscribe({
        next: (res: ProductsByCaravan[]) => this.products.set(res),
        error: (err: any) => console.error('Error loading products', err)
      });
    });
  }

  filterCategory(category: string): void {
    this.selectedCategory = category;
    //  lógica de filtrado (por ahora no)
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
