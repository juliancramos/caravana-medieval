import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class GameStateService {

  constructor(private http: HttpClient) {}

  //consulta roles disponibles para el jugador en la partida
  getTakenRoles(gameId: number) {
    return this.http.get<string[]>(`/api/games-by-player/game/${gameId}/taken-roles`);
  }

  


}
