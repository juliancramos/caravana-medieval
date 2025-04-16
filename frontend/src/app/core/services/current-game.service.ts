import { Injectable, signal } from '@angular/core';
import { GameByPlayer } from '@shared/models/game-by-player.model';

@Injectable({
  providedIn: 'root'
})
export class CurrentGameService {

  constructor() { }
  selectedGame = signal<GameByPlayer | null>(null);

}
