import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { GameByPlayerService } from '../../core/services/game-by-player.service';
import { AuthService } from '../../core/services/auth.service';
import { GameByPlayer } from '../../shared/models/game-by-player.model';
import { CurrentGameService } from '@core/services/current-game.service';

@Component({
  selector: 'select-game',
  imports: [CommonModule],
  templateUrl: './select-game.component.html',
  styleUrls: ['./select-game.component.scss']
})
export class SelectGameComponent implements OnInit {

  games = signal<GameByPlayer[]>([]);
  private currentGame = inject(CurrentGameService);

  //readonly solo para remarcar que es solo de lectura
  readonly gameSlots = computed< (GameByPlayer | null)[] >(() => {
    const existing = this.games();
    //pasa una copia de las partidas existentes y si son menos de 3 rellena con null
    const finalGames: (GameByPlayer | null)[] = [...existing];
    while (finalGames.length < 3) finalGames.push(null);
    return finalGames;
  });


  constructor(
    private gameByPlayerService: GameByPlayerService,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    const playerId = this.authService.user()?.id;
    if (!playerId) return;

    this.gameByPlayerService.getGamesByPlayer(playerId).subscribe({
      //next es la función que se ejectura despues de respuesta exitosa
      //Data es el array de partidas recibidas
      next: (data) => this.games.set(data),
      error: err => console.error('Error loading games', err)
    });
  }


  loadGame(game: GameByPlayer): void {
    this.currentGame.selectedGame.set(game);
    this.router.navigate(['/resume']);
  }

  createNewGame(): void {
    this.router.navigate(['/select-caravan']);
  }

  trackByIndex(index: number): number {
    return index;
  }

}

