import { Component, OnInit, signal, computed, inject } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { GameByPlayerService } from '@core/services/game-by-player.service';
import { GameByPlayer } from '@shared/models/game-by-player.model';
import { CurrentGameService } from '@core/services/current-game.service';
import { JoinGamePopupComponent } from './join-game-popup/join-game-popup.component';

@Component({
  selector: 'select-game',
  standalone: true,
  imports: [CommonModule, JoinGamePopupComponent],
  templateUrl: './select-game.component.html',
  styleUrls: ['./select-game.component.scss']
})
export class SelectGameComponent implements OnInit {

  games = signal<GameByPlayer[]>([]);
  private currentGame = inject(CurrentGameService);

  readonly gameSlots = computed<(GameByPlayer | null)[]>(() => {
    const existing = this.games();
    const finalGames: (GameByPlayer | null)[] = [...existing];
    while (finalGames.length < 3) finalGames.push(null);
    return finalGames;
  });

  //signal para popup de unirse a un juego existente
  showJoinPopup = signal(false);


  constructor(
    private gameByPlayerService: GameByPlayerService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.gameByPlayerService.getMyGames().subscribe({
      next: (data) => this.games.set(data),
      error: err => console.error('Error loading games', err)
    });
  }

  loadGame(game: GameByPlayer): void {
    this.currentGame.selectedGame.set(game);
    this.router.navigate(['/select-role'], { queryParams: { returnTo: 'resume' } });
  }


  createNewGame(): void {
    this.router.navigate(['/select-caravan']);
  }

  trackByIndex(index: number): number {
    return index;
  }
  // evita que el clic llegue al div padre
  onJoinClick(event: MouseEvent): void {
    event.stopPropagation(); 
    this.showJoinPopup.set(true);
  }

}
