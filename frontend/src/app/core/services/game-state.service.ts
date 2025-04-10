// src/app/core/services/game-state.service.ts
import {computed, Injectable, signal} from '@angular/core';

@Injectable({ providedIn: 'root' })
export class GameStateService {
  // Signal for current city
  private _currentCity = signal('Eldenport');
  // Reactive getter
  currentCity = computed(() => this._currentCity());

// Upload current city
  setCurrentCity(newCity: string) {
    this._currentCity.set(newCity);
  }

  // Health Signal
  private _playerHealth = signal(100);
  playerHealth = computed(() => this._playerHealth());

  updateHealth(amount: number) {
    this._playerHealth.set(this._playerHealth() + amount);
  }

  // Gold Signal
  private _playerGold = signal(250);
  playerGold = computed(() => this._playerGold());

  updateGold(amount: number) {
    this._playerGold.set(this._playerGold() + amount);
  }

}
