import { Component, effect } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import {GameStatusBarComponent} from '@shared/game-status-bar/game-status-bar.component';
import {ServicePopupComponent} from '@shared/service-popup/service-popup.component';
import {AuthService} from '@core/services/auth.service';
import { CurrentGameService } from '@core/services/current-game.service';

@Component({
  selector: 'app-resume',
  standalone: true,
  imports: [CommonModule, GameStatusBarComponent, ServicePopupComponent],
  templateUrl: './resume.component.html',
  styleUrls: ['./resume.component.scss']
})
export class ResumeComponent {

  constructor( private router: Router, 
    public authService: AuthService,
    public currentGame: CurrentGameService
  ) {
    effect(() => {
      const gold = this.currentGame.availableMoney();
      console.log('Gold actualizado:', gold);
    });
  }

  selectedService: any = null;


  // Efecto para iniciar el seguimiento de la partida actual
  ngOnInit(): void {
    this.currentGame.startGameSyncPolling();
  }



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
