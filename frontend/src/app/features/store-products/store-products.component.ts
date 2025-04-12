import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {GameStatusBarComponent} from "@shared/game-status-bar/game-status-bar.component";

@Component({
  selector: 'app-store-products',
  standalone: true,
  templateUrl: './store-products.component.html',
  styleUrls: ['./store-products.component.scss'],
    imports: [CommonModule, GameStatusBarComponent]
})
export class StoreProductsComponent {
  constructor(private router: Router) {}
  selectedService: any = null;
  itemsPerPage = 9;
  currentPage = 0;


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


  showGlobalMessage(message: string, type: 'success' | 'error' = 'success'): void {
    this.globalNotification = message;
    this.notificationType = type;
    this.showGlobalNotification = true;
    setTimeout(() => {
      this.showGlobalNotification = false;
    }, 2000);
  }

  showServiceInfo(service: any): void {
    this.selectedService = service;
  }

  closeServiceInfo(): void {
    this.selectedService = null;
  }
}
