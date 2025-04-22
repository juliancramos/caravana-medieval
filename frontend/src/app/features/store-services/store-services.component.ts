import {Component, OnInit} from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import {GameStatusBarComponent} from '@shared/game-status-bar/game-status-bar.component';
import {ServicePopupComponent} from '@shared/service-popup/service-popup.component';
import { ServicesService, Service } from '@core/services/services.service';
import {HttpClient} from '@angular/common/http';
import {CurrentGameService} from '@core/services/current-game.service';
import {ServiceForCity} from '@shared/models/ServiceForCity';



@Component({
  selector: 'app-store-services',
  standalone: true,
  imports: [CommonModule, GameStatusBarComponent, ServicePopupComponent],
  templateUrl: './store-services.component.html',
  styleUrls: ['./store-services.component.scss']
})
export class StoreServicesComponent implements OnInit {
  constructor(
    private router: Router,
    private servicesService: ServicesService,
    public currentGame: CurrentGameService,
    private http: HttpClient
  ) {}
  selectedServiceToBuy: any = null;  // para el popup de compra
  selectedServiceInfo: any = null;   // para el popup de la barra superior
  goldChanged = false;
  //Global notification
  globalNotification = '';
  showGlobalNotification = false;
  notificationType: 'success' | 'error' = 'success';

  services: ServiceForCity[] = [];

  ngOnInit(): void {
    const cityId = this.currentGame.selectedGame()?.game.caravan.currentCity.idCity;
    if (!cityId) return;

    this.servicesService.getAvailableServicesByCity(cityId).subscribe({
      next: data => this.services = data,
      error: () => this.showGlobalMessage('No se pudieron cargar los servicios.', 'error')
    });
  }

  openServicePopup(service: any): void {
    this.selectedServiceToBuy = service;
  }

  closeServicePopup(): void {
    this.selectedServiceToBuy = null;
  }

  showServiceInfo(service: any): void {
    this.selectedServiceInfo = service;
  }

  closeServiceInfo(): void {
    this.selectedServiceInfo = null;
  }

//Buy service with signals

  buyService() {

    const service = this.selectedServiceToBuy;
    const gameId = this.currentGame.selectedGame()?.game.idGame;
    const serviceId = service?.serviceId;
    if (!gameId || !serviceId) {
      return;
    }


    this.http.post(`/api/services-by-city/buy?gameId=${gameId}&serviceId=${serviceId}`, null).subscribe({
      next: () => {
        this.showGlobalMessage('¡Servicio comprado!', 'success');
        this.currentGame.refreshGame(gameId);
        this.services = this.services.filter(s => s.serviceId !== service.serviceId);
        this.closeServicePopup();
      },
      error: err => {
        console.error('[BUY SERVICE] Error en compra:', err);
        const msg = err.error?.message ?? 'No se pudo comprar el servicio.';
        this.showGlobalMessage(msg, 'error');
      }
    });
  }
  showGlobalMessage(message: string, type: 'success' | 'error' = 'success'): void {
    this.globalNotification = message;
    this.notificationType = type;
    this.showGlobalNotification = true;
    setTimeout(() => {
      this.showGlobalNotification = false;
    }, 2000);
  }
  exitStore(): void {
    this.router.navigate(['/resume']);
  }
}
