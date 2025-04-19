import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InventoryItemComponent } from '@shared/inventory-item/inventory-item.component';
import { BaseProductItem } from '@shared/models/base-product-item';

@Component({
  selector: 'app-inventory-panel',
  standalone: true,
  imports: [CommonModule, InventoryItemComponent],
  templateUrl: './inventory-panel.component.html',
  styleUrls: ['./inventory-panel.component.scss']
})
export class InventoryPanelComponent<T extends BaseProductItem = BaseProductItem> {
  @Input() title: string = '';
  @Input() items: () => T[] = () => [];
  @Input() showFilters: boolean = true;
  @Input() variant: 'inventory' | 'store' | 'services' = 'inventory';

  @Output() itemSelected = new EventEmitter<T>();

  selectedCategory = 'all';

  filterCategory(category: string): void {
    this.selectedCategory = category;
  }

  onItemClicked(item: T): void {
    this.itemSelected.emit(item);
  }
}
