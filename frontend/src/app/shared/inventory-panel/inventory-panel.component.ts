import { Component, Input, Output, EventEmitter, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryItemComponent } from '@shared/inventory-item/inventory-item.component';
import { ProductPopupComponent } from '@shared/product-popup/product-popup.component';
import { ProductWithQuantity } from '@shared/models/product-with-quantity';

@Component({
  selector: 'app-inventory-panel',
  imports: [CommonModule, InventoryItemComponent, ProductPopupComponent],
  templateUrl: './inventory-panel.component.html',
  styleUrls: ['./inventory-panel.component.scss']
})
export class InventoryPanelComponent {
  @Input() title: string = '';
  @Input() items: () => ProductWithQuantity[] = () => [];
  @Input() showFilters: boolean = true;


  selectedItem: ProductWithQuantity | null = null;
  selectedCategory = 'all';

  filterCategory(category: string): void {
    this.selectedCategory = category;
    // lógica de filtrado (por ahora no)
  }

  onItemClicked(item: ProductWithQuantity): void {
    this.selectedItem = item;
  }
}
