import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import {ServiceForCity} from '@shared/models/ServiceForCity';

export interface Service {
  id: number;
  name: string;
  description: string;
  price: number;
  imgUrl: string;
}

@Injectable({ providedIn: 'root' })
export class ServicesService {
  constructor(private http: HttpClient) {}


  getAvailableServicesByCity(cityId: number) {
    return this.http.get<ServiceForCity[]>(`/api/services-by-city/available/city/${cityId}`);
  }



}
