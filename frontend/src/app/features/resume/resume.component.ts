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


  //Reactive playerHealth getter
  get playerHealth(){
    return this.gameState.playerHealth();
  }

  //Reactive playerGold getter
  get playerGold(){
    return this.gameState.playerGold();
  }



  // Reactive current city getter
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

  goToInventory(): void {
    this.router.navigate(['/inventory']);
  }

  showServiceInfo(service: any): void {
    this.selectedService = service;
  }

  closeServiceInfo(): void {
    this.selectedService = null;
  }

  //Example: Buy a product which costs 50 gold coins
  buyExampleProduct() {
    const price = 50;
    if (this.playerGold >= price) {
      this.gameState.updateGold(-price);
      alert('¡Producto comprado!');
    } else {
      alert('No tienes suficiente oro.');
    }
  }

  // Example: When you receive damage
  takeDamage() {
    this.gameState.updateHealth(-20);
  }
}
