import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryItemComponent } from '@shared/inventory-item/inventory-item.component';
import { ProductWithQuantity } from '@shared/models/product-with-quantity';

@Component({
  selector: 'app-inventory-panel',
  imports: [CommonModule, InventoryItemComponent],
  templateUrl: './inventory-panel.component.html',
  styleUrls: ['./inventory-panel.component.scss']
})
export class InventoryPanelComponent {
  @Input() title: string = '';
  @Input() items: () => ProductWithQuantity[] = () => [];
  @Input() showFilters: boolean = true;
  @Input() variant: 'inventory' | 'store' | 'services' = 'inventory';

  @Output() itemSelected = new EventEmitter<ProductWithQuantity>();

  selectedCategory = 'all';

  filterCategory(category: string): void {
    this.selectedCategory = category;
  }

  onItemClicked(item: ProductWithQuantity): void {
    this.itemSelected.emit(item); //notifica al padre
  }
}
