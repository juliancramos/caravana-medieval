import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-inventory',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './inventory.component.html',
  styleUrls: ['./inventory.component.scss']
})
export class InventoryComponent {
  selectedCategory = 'all';

  constructor(private router: Router) {}
  itemsPerPage = 8;
  currentPage = 0;

  inventoryItems = [
    { name: 'Especias', icon: '/assets/icons/bag.png', quantity: 5, category: 'recursos' },
    { name: 'Armas', icon: '/assets/icons/bag.png', quantity: 1, category: 'armas' },
    { name: 'Pociones', icon: '/assets/icons/bag.png', quantity: 8, category: 'alquimia' },
    { name: 'Especias', icon: '/assets/icons/bag.png', quantity: 5, category: 'recursos' },
    { name: 'Armas', icon: '/assets/icons/bag.png', quantity: 1, category: 'armas' },
    { name: 'Pociones', icon: '/assets/icons/bag.png', quantity: 8, category: 'alquimia' },
    { name: 'Especias', icon: '/assets/icons/bag.png', quantity: 5, category: 'recursos' },
    { name: 'Armas', icon: '/assets/icons/bag.png', quantity: 1, category: 'armas' },
    { name: 'Pociones', icon: '/assets/icons/bag.png', quantity: 8, category: 'alquimia' },
    { name: 'Especias', icon: '/assets/icons/bag.png', quantity: 5, category: 'recursos' },
    { name: 'Armas', icon: '/assets/icons/bag.png', quantity: 1, category: 'armas' },
    { name: 'Pociones', icon: '/assets/icons/bag.png', quantity: 8, category: 'alquimia' },
  ];



  prevPage(): void {
    if (this.currentPage > 0) this.currentPage--;
  }
  exitInventory(): void {
    this.router.navigate(['/resume']);
  }

  nextPage(): void {
    const totalPages = Math.ceil(this.inventoryItems.length / this.itemsPerPage);
    if (this.currentPage < totalPages - 1) this.currentPage++;
  }

  filterCategory(category: string) {
    this.selectedCategory = category;
  }

  get paginatedItems() {
    const start = this.currentPage * this.itemsPerPage;
    return this.filteredItems.slice(start, start + this.itemsPerPage);
  }


  get filteredItems() {
    return this.selectedCategory === 'all'
      ? this.inventoryItems
      : this.inventoryItems.filter(item => item.category === this.selectedCategory);
  }

}
