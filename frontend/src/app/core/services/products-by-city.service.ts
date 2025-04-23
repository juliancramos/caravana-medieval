import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { ProductsByCity } from '@shared/models/products-by-city';

@Injectable({
  providedIn: 'root'
})
export class ProductsByCityService {

  private http = inject(HttpClient);
  
  getProductsByCity(cityId: number){
    return this.http.get<ProductsByCity[]>(`/api/products-by-city/products/city/${cityId}`);
  }

}
