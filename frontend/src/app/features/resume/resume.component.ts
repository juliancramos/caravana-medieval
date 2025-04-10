import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { GameStateService } from '@core/services/game-state.service';

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.scss']
})
export class ResumeComponent {

  constructor(private gameState: GameStateService, private router: Router) {}

  playerHealth = 100;
  playerGold = 250;

  // Reactive getter
  get currentCity() {
    return this.gameState.currentCity();
  }

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

  selectedService: any = null;

  goToProducts(): void {
    this.router.navigate(['/productos']);
  }

  goToServices(): void {
    this.router.navigate(['/servicios']);
  }

  goToMap(): void {
    this.router.navigate(['/mapa']);
  }

  showServiceInfo(service: any): void {
    this.selectedService = service;
  }

  closeServiceInfo(): void {
    this.selectedService = null;
  }
}
