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

  availableMoney = this.currentGame.availableMoney;


  caravanName = this.currentGame.caravanName;
  lifePoints = this.currentGame.lifePoints;
  

  minProfit = this.currentGame.minProfit;
  cityName = this.currentGame.cityName;

  remainingTime = this.currentGame.remainingTime;
  

  constructor(public gameState: GameStateService) {}

  @Output() serviceSelected = new EventEmitter<any>();

  showServiceInfo(service: any) {
    this.serviceSelected.emit(service);
  }
}
