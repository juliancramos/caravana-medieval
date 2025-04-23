import { Component, Input, Output, EventEmitter, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductForStore } from '@shared/models/product-for-store';

@Component({
  selector: 'app-product-store-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-store-popup.component.html',
  styleUrls: ['./product-store-popup.component.scss']
})
export class ProductStorePopupComponent {
  @Input() product!: ProductForStore;
  @Output() close = new EventEmitter<void>();
  @Output() buy = new EventEmitter<{ product: ProductForStore; quantity: number }>();

  quantity = signal(1);

  totalPrice = computed(() => this.product.price * this.quantity());


  increase(): void {
    this.quantity.update(q => q + 1);
  }

  decrease(): void {
    this.quantity.update(q => Math.max(1, q - 1));
  }

  closePopup(): void {
    this.close.emit();
  }

  confirmPurchase(): void {
    this.buy.emit({ product: this.product, quantity: this.quantity() });
  }
}


