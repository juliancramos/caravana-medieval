import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ProductsByCaravan } from '@shared/models/products-by-caravan';

@Injectable({ providedIn: 'root' })
export class ProductsByCaravanService {
  private http = inject(HttpClient);

  getProductsByCaravan(caravanId: number) {
    return this.http.get<ProductsByCaravan[]>(`/api/products-by-caravan/products/caravan/${caravanId}`);
  }
}
