import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GameByPlayer } from '@shared/models/game-by-player.model';

@Injectable({ providedIn: 'root' })
export class GameByPlayerService {

  constructor(private http: HttpClient) {}

  getGamesByPlayer(playerId: number): Observable<GameByPlayer[]> {
    return this.http.get<GameByPlayer[]>(`/api/games-by-player/dtos/player/${playerId}`);
  }

  createGame(gameDTO: any) {
    return this.http.post<GameByPlayer>('/api/game/create', gameDTO);
  }

}
