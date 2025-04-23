import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-product-list',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './product-list.component.html',
})
export class ProductListComponent implements OnInit {
  productNames: string[] = [];

  constructor(private http: HttpClient) {}

  ngOnInit(): void {
    this.http.get<any[]>('/api/product/products').subscribe({
      next: (products) => {
        this.productNames = products.map(p => p.name);
      },
      error: (err) => {
        console.error('Error al obtener productos:', err);
      }
    });
  }
}
