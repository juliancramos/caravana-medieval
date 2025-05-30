import { Component, OnInit, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MapService } from '@core/services/map.service';
import { MapData } from '@shared/models/map.model';
import { CurrentGameService } from '@core/services/current-game.service';
import { GameByPlayerService } from '@core/services/game-by-player.service';
import { AuthService } from '@core/services/auth.service';
import { GameCreationService } from '@core/services/game-creation.service';
import { GameByPlayer } from '@shared/models/game-by-player.model';
import { Game } from '@shared/models/game.model';

@Component({
  selector: 'app-select-map',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './select-map.component.html',
  styleUrls: ['./select-map.component.scss']
})
export class SelectMapComponent implements OnInit {
  maps = signal<MapData[]>([]);
  private mapService = inject(MapService);
  private currentGame = inject(CurrentGameService);
  private gameService = inject(GameByPlayerService);
  private authService = inject(AuthService);
  private gameCreation = inject(GameCreationService);
  private router = inject(Router);

  ngOnInit(): void {
    this.mapService.getAllMaps().subscribe({
      next: data => this.maps.set(data.slice(0, 3)),
      error: err => console.error('Error loading maps:', err)
    });
  }

  selectMap(map: MapData): void {
    const caravan = this.gameCreation.getCaravan();
    const difficulty = this.gameCreation.getDifficulty();

    if (!caravan || !difficulty) {
      console.error('❌ Faltan datos para crear la partida');
      return;
    }

    const gameDTO = {
      elapsedTime: 0,
      timeLimit: difficulty.timeLimit,
      minProfit: difficulty.goalMoney,
      caravanId: caravan.idCaravan,
      mapId: map.idMap
    };

    this.gameService.createGame(gameDTO).subscribe({
      next: (game: Game) => {
        // Ya no usamos playerId. El backend lo deduce desde el token JWT
        this.gameService.assignMeToGame(game.idGame).subscribe({
          next: () => {
            const wrapper: GameByPlayer = { game };
            this.currentGame.selectedGame.set(wrapper);
            this.router.navigate(['/resume']);
          },
          error: err => console.error('❌ Error asignando jugador:', err)
        });
      },
      error: err => console.error('❌ Error creando partida:', err)
    });
  }
}
