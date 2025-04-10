import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import {GameStateService} from '@core/services/game-state.service';

@Component({
  selector: 'app-store-services',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './store-services.component.html',
  styleUrls: ['./store-services.component.scss']
})
export class StoreServicesComponent {
  constructor(private gameState: GameStateService, private router: Router) {}
  selectedService: any = null;
  goldChanged = false;

  services = [
    {
      name: 'Guardias',
      icon: '/assets/icons/shield.png',
      description: 'Reduce el daño recibido en rutas inseguras en un 25%.',
      duration: 'Permanente',
      price: 120
    },
    {
      name: 'Mejorar velocidad',
      icon: '/assets/icons/speed.png',
      description: 'Aumenta la velocidad de la caravana hasta un 50%.',
      duration: 'Permanente',
      price: 150
    },
    {
      name: 'Mejorar capacidad',
      icon: '/assets/icons/bag.png',
      description: 'Aumenta la capacidad de carga hasta un 400%.',
      duration: 'Permanente',
      price: 200
    },
    {
      name: 'Reparar',
      icon: '/assets/icons/repair.png',
      description: 'Recupera parte o todos los puntos de vida.',
      duration: 'Inmediato',
      price: 100
    }
  ];

  get playerGold(){
    return this.gameState.playerGold();
  }





  openServicePopup(service: any): void {
    this.selectedService = service;
  }

  closeServicePopup(): void {
    this.selectedService = null;
  }

//Buy service with signals

  buyService() {
    const total = this.selectedService.price;
    if (this.playerGold >= total) {
      this.gameState.updateGold(-total);
      this.goldChanged = true;
      setTimeout(() => this.goldChanged = false, 800);
      this.closeServicePopup();
    } else {
      alert('No tienes suficiente oro.');
    }
  }





  exitStore(): void {
    this.router.navigate(['/resume']);
  }
}
