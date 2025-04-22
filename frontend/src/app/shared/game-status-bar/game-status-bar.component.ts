import {Component, computed, EventEmitter, inject, OnInit, Output} from '@angular/core';
import { CommonModule } from '@angular/common';
import { CurrentGameService } from '@core/services/current-game.service';
import {ActiveServicesBarComponent} from '@shared/active-services-bar/active-services-bar.component';

@Component({
  selector: 'app-game-status-bar',
  standalone: true,
  imports: [CommonModule, ActiveServicesBarComponent],
  templateUrl: './game-status-bar.component.html',
  styleUrls: ['./game-status-bar.component.scss']
})
export class GameStatusBarComponent {


  currentGame = inject(CurrentGameService);

  availableMoney = this.currentGame.availableMoney;
  activeServices = this.currentGame.activeServices;



  caravanName = this.currentGame.caravanName;
  lifePoints = this.currentGame.lifePoints;


  minProfit = this.currentGame.minProfit;
  cityName = this.currentGame.cityName;

  remainingTime = this.currentGame.remainingTime;

  currentLoad = this.currentGame.caravanCurrentLoad;
  maxCapacity = computed(() =>
    this.currentGame.selectedGame()?.game.caravan.maxCapacity ?? 100
  );


  @Output() serviceSelected = new EventEmitter<any>();

  showServiceInfo(service: any) {
    this.serviceSelected.emit(service);
  }



}
