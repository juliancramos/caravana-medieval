import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Caravan } from '@shared/models/caravan.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class CaravanService {
  constructor(private http: HttpClient) {}

  getAllCaravans(): Observable<Caravan[]> {
    return this.http.get<Caravan[]>('/api/caravan/caravans');
  }
}
