import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';

export interface BuyProductDTO {
  productId: number;
  quantity: number;
  caravanId: number;
  cityId: number;
}

@Injectable({
  providedIn: 'root'
})
export class StoreService {
  private http = inject(HttpClient);
  private readonly baseUrl = '/api/store';

  buyProduct(dto: BuyProductDTO): Observable<void> {
    return this.http.post<void>(`${this.baseUrl}/buy`, dto);
  }
}
