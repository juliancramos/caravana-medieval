import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class GameStateService {
  playerHealth = 100;
  playerGold = 250;
  currentCity = 'Eldenport';
  activeServices = [
    {
      name: 'Guardias',
      icon: '/assets/icons/shield.png',
      description: 'Reduce el daño recibido en rutas inseguras en un 25%.',
      duration: 'Permanente'
    },
    {
      name: 'Mejorar velocidad',
      icon: '/assets/icons/speed.png',
      description: 'Aumenta la velocidad de la caravana hasta un 50%.',
      duration: 'Permanente'
    },
    {
      name: 'Mejorar capacidad',
      icon: '/assets/icons/bag.png',
      description: 'Aumenta la capacidad de carga hasta un 400%.',
      duration: 'Permanente'
    }
  ];



  // Opcionalmente podrías usar getters/setters o signals si en un futuro lo deseas.
}
