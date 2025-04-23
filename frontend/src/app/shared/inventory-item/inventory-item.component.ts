import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-inventory-item',
  imports: [CommonModule],
  templateUrl: './inventory-item.component.html',
  styleUrls: ['./inventory-item.component.scss']
})
export class InventoryItemComponent {
  @Input() imgUrl: string | null = null;
  @Input() name: string = '';
  @Input() quantity: number = 0;

  @Output() itemSelected = new EventEmitter<void>();

  handleClick(): void {
    this.itemSelected.emit();
  }
}
