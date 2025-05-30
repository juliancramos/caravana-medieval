import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { GameByPlayer } from '@shared/models/game-by-player.model';
import { Game } from '@shared/models/game.model';

@Injectable({ providedIn: 'root' })
export class GameByPlayerService {

  constructor(private http: HttpClient) {}

  getGamesByPlayer(playerId: number): Observable<GameByPlayer[]> {
    return this.http.get<GameByPlayer[]>(`/api/games-by-player/dtos/player/${playerId}`);
  }

  getMyGames(): Observable<GameByPlayer[]> {
    return this.http.get<GameByPlayer[]>('/api/games-by-player/my-games');
  }



  createGame(gameDTO: any): Observable<Game> {
    return this.http.post<Game>('/api/game/create', gameDTO);
  }
  assignPlayerToGame(gameId: number, playerId: number) {
    return this.http.post(`/api/games-by-player/assign/games/${gameId}/players/${playerId}`, null);
  }

  assignMeToGame(gameId: number): Observable<void> {
    return this.http.post<void>(`/api/games-by-player/my-game/${gameId}`, {});
  }


}
