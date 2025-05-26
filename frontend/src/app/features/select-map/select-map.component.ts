import { Component, OnInit, inject, signal } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { MapService } from '@core/services/map.service';
import { MapData } from '@shared/models/map.model';
import { CurrentGameService } from '@core/services/current-game.service';
import { GameByPlayerService } from '@core/services/game-by-player.service';
import { AuthService } from '@core/services/auth.service';
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
  private router = inject(Router);

  ngOnInit(): void {
    console.log('🚀 SelectMapComponent cargado');
    this.mapService.getAllMaps().subscribe({
      next: (data) => {
        const firstThreeMaps = data.slice(0, 3);
        this.maps.set(firstThreeMaps);
      },
      error: (err) => console.error('Error loading maps:', err)
    });
    console.log('Este es el archivo de select-map.component.ts que se está ejecutando');

  }

  selectMap(map: MapData): void {
    const current = this.currentGame.selectedGame();
    console.log('✅ selectedGame:', current);

    const caravanId = current?.game.caravan.idCaravan;
    const playerId = this.authService.user()?.id;
    const mapId = map.idMap;

    console.log('🟡 caravanId:', caravanId);
    console.log('🟡 playerId:', playerId);
    console.log('🟡 mapId:', mapId);

    if (!caravanId || !playerId) {
      console.error('❌ Missing caravanId or playerId');
      return;
    }

    const gameDTO = {
      elapsedTime: 0,
      timeLimit: 60,
      minProfit: 1000,
      caravanId,
      mapId
    };

    console.log('📦 Enviando GameDTO:', gameDTO);

    this.gameService.createGame(gameDTO).subscribe({
      next: (game: Game) => {
        const gameId = game.idGame;
        const playerId = this.authService.user()?.id;

        if (!playerId) {
          console.error('❌ No se encontró el ID del jugador');
          return;
        }

        this.gameService.assignPlayerToGame(gameId, playerId).subscribe({
          next: () => {
            const gameWrapper: GameByPlayer = { game }; // 🟢 OK
            this.currentGame.selectedGame.set(gameWrapper); // 🟢 OK
            this.router.navigate(['/resume']);
          },
          error: err => console.error('❌ Error asignando jugador:', err)
        });

      },
      error: err => console.error('❌ Error creando partida:', err)
    });



  }



}
