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
        const mapped = routes
          .map(route => {
            //Encuentra las coordenadas de la ciudad en especifico para poder ubicar en el mapa
            const coord: CityCoordinate | undefined = city_coordinates.find(
              (c: CityCoordinate) => c.id === route.destinationCity.idCity
            );
            //si no encuentra coordenadas no pinta la ciudad
            if (!coord) return null;

            return {
              id: route.destinationCity.idCity,
              name: route.destinationCity.name,
              x: coord.x,
              y: coord.y,
              route
            } satisfies CityWithRoute;
          })
          //Filtra para eliminar los nulos (los de no coordenadas) !!c para evitar que sea null o undefined
          .filter((c): c is CityWithRoute => !!c);

        this.availableCities.set(mapped);
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
