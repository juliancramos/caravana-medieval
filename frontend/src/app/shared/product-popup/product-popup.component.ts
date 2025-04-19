import { Component, EventEmitter, Input, Output, signal, computed } from '@angular/core';
import { CommonModule } from '@angular/common';
import { SellProductDTO } from '@shared/models/sell-product';
import { BaseProductItem } from '@shared/models/base-product-item';

@Component({
  selector: 'app-product-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-popup.component.html',
  styleUrls: ['./product-popup.component.scss']
})
export class ProductPopupComponent {
  @Input() product!: SellProductDTO;

totalPrice = computed(() => this.product.price * this.quantity());

confirmSale(): void {
  this.sell.emit({ product: this.product, quantity: this.quantity() });
}
@Output() sell = new EventEmitter<{ product: SellProductDTO; quantity: number }>();


  @Output() close = new EventEmitter<void>();
  @Output() itemSelected = new EventEmitter<BaseProductItem>();

  quantity = signal(1);


  increase(): void {
    this.quantity.update(q => Math.min(q + 1, this.product.quantity));
  }

  decrease(): void {
    this.quantity.update(q => Math.max(1, q - 1));
  }

  

  onItemClicked(item: BaseProductItem): void {
    this.itemSelected.emit(item);
  }

  closePopup(): void {
    this.close.emit();
  }
}
