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


}
