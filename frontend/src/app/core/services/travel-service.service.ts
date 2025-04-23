import { HttpClient } from '@angular/common/http';
import { Injectable, inject } from '@angular/core';
import { TravelDTO } from '@shared/models/travel.dto';
import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root',
})
export class TravelService {
  private http = inject(HttpClient);
  private readonly baseUrl = '/api/travel';

  travel(dto: TravelDTO): Observable<void> {
    return this.http.post<void>(this.baseUrl, dto);
  }
}
