import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-store-services',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './store-services.component.html',
  styleUrls: ['./store-services.component.scss']
})
export class StoreServicesComponent {
  playerGold = 250;
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

  selectedService: any = null;

  constructor(private router: Router) {}

  openServicePopup(service: any): void {
    this.selectedService = service;
  }

  closeServicePopup(): void {
    this.selectedService = null;
  }

  buyService(): void {
    const total = this.selectedService.price;

    if (this.playerGold >= total) {
      this.updateGold(-total);
      alert(`Has comprado el servicio: ${this.selectedService.name}`);
      this.closeServicePopup();
    } else {
      alert('No tienes suficientes monedas de oro.');
    }
  }

  updateGold(amount: number): void {
    this.playerGold += amount;
    this.goldChanged = false;

    setTimeout(() => {
      this.goldChanged = true;

      setTimeout(() => {
        this.goldChanged = false;
      }, 800);
    });
  }

  exitStore(): void {
    this.router.navigate(['/resume']);
  }
}
