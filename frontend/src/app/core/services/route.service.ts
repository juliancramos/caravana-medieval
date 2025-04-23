import { HttpClient } from '@angular/common/http';
import { inject, Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Route } from '@shared/models/route.model';

@Injectable({
  providedIn: 'root'
})
export class RouteService {
  private http = inject(HttpClient);
  private readonly baseUrl = '/api/route';

  getRoutesFrom(cityId: number): Observable<Route[]> {
    return this.http.get<Route[]>(`${this.baseUrl}/from/${cityId}`);
  }

  getRoutesTo(cityId: number): Observable<Route[]> {
    return this.http.get<Route[]>(`${this.baseUrl}/to/${cityId}`);
  }

}
