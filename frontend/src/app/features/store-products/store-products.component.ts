import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-store-products',
  standalone: true,
  templateUrl: './store-products.component.html',
  styleUrls: ['./store-products.component.scss'],
  imports: [CommonModule]
})
export class StoreProductsComponent {
  constructor(private router: Router) {}

  itemsPerPage = 9;
  currentPage = 0;
  playerGold = 250;
  goldChanged = false;

  selectedProduct: any = null;
  selectedQuantity = 1;

  // Global notification
  globalNotification = '';
  showGlobalNotification = false;
  notificationType: 'success' | 'error' = 'success';

  products = [
    { name: 'Especias', price: 50, image: '/assets/icons/bag.png' },
    { name: 'Telas', price: 70, image: '/assets/icons/bag.png' },
    { name: 'Armas', price: 120, image: '/assets/icons/bag.png' },
    { name: 'Armas', price: 120, image: '/assets/icons/bag.png' },
    { name: 'Armas', price: 120, image: '/assets/icons/bag.png' },
    { name: 'Armas', price: 120, image: '/assets/icons/bag.png' },
    { name: 'Armas', price: 120, image: '/assets/icons/bag.png' },
    { name: 'Armas', price: 120, image: '/assets/icons/bag.png' },
    { name: 'Armas', price: 120, image: '/assets/icons/bag.png' },
    { name: 'Ganado', price: 90, image: '/assets/icons/bag.png' }
  ];

  get paginatedProducts() {
    const start = this.currentPage * this.itemsPerPage;
    return this.products.slice(start, start + this.itemsPerPage);
  }

  prevPage(): void {
    if (this.currentPage > 0) this.currentPage--;
  }

  nextPage(): void {
    const totalPages = Math.ceil(this.products.length / this.itemsPerPage);
    if (this.currentPage < totalPages - 1) this.currentPage++;
  }

  openProductPopup(product: any): void {
    this.selectedProduct = product;
    this.selectedQuantity = 1;
  }

  closeProductPopup(): void {
    this.selectedProduct = null;
  }

  increaseQuantity(): void {
    this.selectedQuantity++;
  }

  decreaseQuantity(): void {
    if (this.selectedQuantity > 1) this.selectedQuantity--;
  }

  exitStore(): void {
    this.router.navigate(['/resume']);
  }

  updateGold(amount: number): void {
    this.playerGold += amount;
    this.goldChanged = true;
    setTimeout(() => {
      this.goldChanged = false;
    }, 800);
  }

  // Buy Product
  buyProduct(): void {
    const product = this.selectedProduct;
    const total = product.price * this.selectedQuantity;

    if (this.playerGold >= total) {
      this.updateGold(-total);
      this.closeProductPopup();
      this.showGlobalMessage('¡Producto comprado!', 'success');
    } else {
      this.closeProductPopup();
      this.showGlobalMessage('No tienes suficiente oro.', 'error');
    }
  }

  showGlobalMessage(message: string, type: 'success' | 'error' = 'success'): void {
    this.globalNotification = message;
    this.notificationType = type;
    this.showGlobalNotification = true;
    setTimeout(() => {
      this.showGlobalNotification = false;
    }, 2000);
  }
}
