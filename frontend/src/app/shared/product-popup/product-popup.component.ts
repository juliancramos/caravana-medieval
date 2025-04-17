import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductsByCaravan } from '@shared/models/products-by-caravan';

@Component({
  selector: 'app-product-popup',
  imports: [CommonModule],
  templateUrl: './product-popup.component.html',
  styleUrls: ['./product-popup.component.scss']
})
export class ProductPopupComponent {
  @Input() product!: ProductsByCaravan;
  //Output para avisar al componente padre
  @Output() close = new EventEmitter<void>();

  closePopup(): void {
    this.close.emit();
  }
}
