import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { GameStateService } from '@core/services/game-state.service';
import {GameStatusBarComponent} from '@shared/game-status-bar/game-status-bar.component';
import {ServicePopupComponent} from '@shared/service-popup/service-popup.component';

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [CommonModule, GameStatusBarComponent, ServicePopupComponent],
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.scss']
})
export class ResumeComponent {

  constructor(private gameState: GameStateService, private router: Router) {}

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


}
