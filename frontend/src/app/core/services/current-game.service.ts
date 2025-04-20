import { computed, Injectable, signal } from '@angular/core';
import { GameByPlayer } from '@shared/models/game-by-player.model';

@Injectable({
  providedIn: 'root'
})
export class CurrentGameService {
  // estado general de la partida
  selectedGame = signal<GameByPlayer | null>(null);

  //para el header
  availableMoney = computed(() => this.selectedGame()?.game.caravan.availableMoney ?? 0);
  caravanName = computed(() => this.selectedGame()?.game?.caravan?.name);
  lifePoints = computed(() => this.selectedGame()?.game.caravan.lifePoints);
  minProfit = computed(() => this.selectedGame()?.game.minProfit);
  cityName = computed(() => this.selectedGame()?.game.caravan.currentCity.name);

  remainingTime = computed(() => {
    const game = this.selectedGame()?.game;
    if (!game) return 0;
  
    return game.timeLimit - game.elapsedTime;
  });


  constructor() {}

  //Actualizar señales
  updateAvailableGold(productPrice: number): void {
    const current = this.selectedGame();
    if (!current) return;
  
    const updated = { ...current };
    updated.game.caravan.availableMoney += productPrice;
  
    this.selectedGame.set(updated); 
  }

  updateLifePoints(delta: number): void {
    const current = this.selectedGame();
    if (!current) return;
  
    const updated = { ...current };
    updated.game.caravan.lifePoints += delta;
    this.selectedGame.set(updated);
  }
  
  updateElapsedTime(delta: number): void {
    const current = this.selectedGame();
    if (!current) return;
  
    const updated = { ...current };
    updated.game.elapsedTime += delta;
    this.selectedGame.set(updated);
  }
  
  updateCurrentCity(cityId: number, cityName: string): void {
    const current = this.selectedGame();
    if (!current) return;
  
    const updated = { ...current };
    updated.game.caravan.currentCity.idCity = cityId;
    updated.game.caravan.currentCity.name = cityName;
    this.selectedGame.set(updated);
  }
  
  

}
