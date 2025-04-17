import { Component, Input, Output, EventEmitter, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { ProductsByCaravan } from '@shared/models/products-by-caravan';
import { InventoryItemComponent } from '@shared/inventory-item/inventory-item.component';
import { ProductPopupComponent } from '@shared/product-popup/product-popup.component';

@Component({
  selector: 'app-inventory-panel',
  imports: [CommonModule, InventoryItemComponent, ProductPopupComponent],
  templateUrl: './inventory-panel.component.html',
  styleUrls: ['./inventory-panel.component.scss']
})
export class InventoryPanelComponent {
  @Input() title: string = '';
  @Input() items: () => ProductsByCaravan[] = () => [];
  @Input() showFilters: boolean = true;


  selectedItem: ProductsByCaravan | null = null;
  selectedCategory = 'all';

  filterCategory(category: string): void {
    this.selectedCategory = category;
    // lógica de filtrado (por ahora no)
  }

  onItemClicked(item: ProductsByCaravan): void {
    this.selectedItem = item;
  }
}
