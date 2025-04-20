import { Component, computed, effect, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { GameStatusBarComponent } from '@shared/game-status-bar/game-status-bar.component';
import { ServicePopupComponent } from '@shared/service-popup/service-popup.component';
import { RouteService } from '@core/services/route.service';
import { CurrentGameService } from '@core/services/current-game.service';
import { Route } from '@shared/models/route.model';
import { city_coordinates, CityCoordinate } from '@shared/data/city-coordinates';
import { CityWithRoute } from '@shared/models/city-with-route';

@Component({
  selector: 'app-map',
  standalone: true,
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss'],
  imports: [CommonModule, GameStatusBarComponent, ServicePopupComponent]
})
export class MapComponent {
  private router = inject(Router);
  private routeService = inject(RouteService);
  private currentGame = inject(CurrentGameService);

  selectedCity = signal<CityWithRoute | null>(null);
  selectedService = signal<any>(null);

  availableCities = signal<CityWithRoute[]>([]);

  constructor() {
    effect(() => this.loadRoutes());
  }

  loadRoutes() {
    const currentCityId = this.currentGame.selectedGame()?.game.caravan.currentCity.idCity;
    if (!currentCityId) return;
  
    this.routeService.getRoutesFrom(currentCityId).subscribe({
      next: (routes: Route[]) => {
        const cityMap = new Map<number, CityWithRoute>();
  
        routes.forEach(route => {
          const coord = city_coordinates.find(c => c.id === route.destinationCity.idCity);
          if (!coord) return;
  
          const cityId = route.destinationCity.idCity;
  
          if (!cityMap.has(cityId)) {
            cityMap.set(cityId, {
              id: cityId,
              name: route.destinationCity.name,
              x: coord.x,
              y: coord.y,
              routes: [route]
            });
          } else {
            cityMap.get(cityId)!.routes.push(route);
          }
        });
  
        this.availableCities.set(Array.from(cityMap.values()));
      }
    });
  }
  

  openPopup(city: CityWithRoute): void {
    this.selectedCity.set(city);
  }

  closePopup(): void {
    this.selectedCity.set(null);
  }

  goBack(): void {
    this.router.navigate(['/resume']);
  }

  showServiceInfo(service: any): void {
    this.selectedService.set(service);
  }

  closeServiceInfo(): void {
    this.selectedService.set(null);
  }

  get currentCityName() {
    return this.currentGame.selectedGame()?.game.caravan.currentCity.name;
  }
}
