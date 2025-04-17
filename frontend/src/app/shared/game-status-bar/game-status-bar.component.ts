import { Component, computed, EventEmitter, inject, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameStateService } from '@core/services/game-state.service';
import { CurrentGameService } from '@core/services/current-game.service';

@Component({
  selector: 'app-game-status-bar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game-status-bar.component.html',
  styleUrls: ['./game-status-bar.component.scss']
})
export class GameStatusBarComponent {

  currentGame = inject(CurrentGameService);

  caravanName = computed(() => this.currentGame.selectedGame()?.game?.caravan?.name);
  lifePoints = computed(() => this.currentGame.selectedGame()?.game.caravan.lifePoints);
  availableMoney = computed(() => this.currentGame.selectedGame()?.game.caravan.availableMoney);
  minProfit = computed(() => this.currentGame.selectedGame()?.game.minProfit);
  cityName = computed(() => this.currentGame.selectedGame()?.game.caravan.currentCity.name);

  remainingTime = computed(() => {
    const game = this.currentGame.selectedGame()?.game;
    if (!game) return 0;
  
    return game.timeLimit - game.elapsedTime;
  });
  




  constructor(public gameState: GameStateService) {}

  @Output() serviceSelected = new EventEmitter<any>();

  showServiceInfo(service: any) {
    this.serviceSelected.emit(service);
  }
}
