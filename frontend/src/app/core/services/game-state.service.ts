import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class GameStateService {
  playerHealth = 100;
  playerGold = 250;
  currentCity = 'Eldenport';
  activeServices = [
    { icon: '/assets/icons/shield.png', name: 'Protección' },
    { icon: '/assets/icons/bag.png', name: 'Capacidad' },
    { icon: '/assets/icons/speed.png', name: 'Velocidad' }
  ];



  // Opcionalmente podrías usar getters/setters o signals si en un futuro lo deseas.
}
