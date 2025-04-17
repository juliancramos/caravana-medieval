import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductWithQuantity } from '@shared/models/product-with-quantity';

@Component({
  selector: 'app-product-popup',
  imports: [CommonModule],
  templateUrl: './product-popup.component.html',
  styleUrls: ['./product-popup.component.scss']
})
export class ProductPopupComponent {
  @Input() product!: ProductWithQuantity;
  //Output para avisar al componente padre
  @Output() close = new EventEmitter<void>();

  closePopup(): void {
    this.close.emit();
  }
}
