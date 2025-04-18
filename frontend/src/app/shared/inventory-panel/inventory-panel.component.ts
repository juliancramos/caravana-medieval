import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryItemComponent } from '@shared/inventory-item/inventory-item.component';
import { ProductDisplayItem } from '@shared/models/product-display-item';

@Component({
  selector: 'app-inventory-panel',
  standalone: true,
  imports: [CommonModule, InventoryItemComponent],
  templateUrl: './inventory-panel.component.html',
  styleUrls: ['./inventory-panel.component.scss']
})
export class InventoryPanelComponent {
  @Input() title: string = '';
  @Input() items: () => ProductDisplayItem[] = () => [];
  @Input() showFilters: boolean = true;
  @Input() variant: 'inventory' | 'store' | 'services' = 'inventory';

  @Output() itemSelected = new EventEmitter<ProductDisplayItem>();

  selectedCategory = 'all';

  filterCategory(category: string): void {
    this.selectedCategory = category;
  }

  onItemClicked(item: ProductDisplayItem): void {
    this.itemSelected.emit(item);
  }
}
