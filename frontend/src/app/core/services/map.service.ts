import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MapData } from '@shared/models/map.model';
import { Observable } from 'rxjs';

@Injectable({ providedIn: 'root' })
export class MapService {
  constructor(private http: HttpClient) {}

  getAllMaps(): Observable<MapData[]> {
    return this.http.get<MapData[]>('/api/map/maps');
  }
}
