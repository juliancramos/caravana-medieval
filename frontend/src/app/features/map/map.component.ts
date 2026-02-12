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
import { TravelService } from '@core/services/travel-service.service';
import { TravelDTO } from '@shared/models/travel.dto';

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

  activeEffects = this.currentGame.activeEffects;

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

  //VIAJAR
  private travelService = inject(TravelService);
  //Señal para animación de viaje
  isTraveling = signal(false);


  travelTo(route: Route): void {
    const game = this.currentGame.selectedGame();
    if (!game) return;

    const dto: TravelDTO = {
      caravanId: game.game.caravan.idCaravan,
      gameId: game.game.idGame,
      routeId: route.idRoute
    };

    this.isTraveling.set(true); // para mostrar animación

    this.travelService.travel(dto).subscribe({
      next: () => {
        // efecto de "Guardias"
        let damage = route.damage;
        const hasGuardService = this.currentGame.activeServices().some(s => s.name.toLowerCase() === 'guardias');
        if (hasGuardService) {
          damage = Math.floor(damage * 0.75);
        }

        this.currentGame.refreshGame(game.game.idGame);

        /* actualizar señales
        this.currentGame.updateLifePoints(-route.damage);
        this.currentGame.updateElapsedTime(route.travelTime);
        this.currentGame.updateCurrentCity(route.destinationCity.idCity, route.destinationCity.name);
*/
        // esperar un momento para mostrar animación y luego navegar
        setTimeout(() => {
          this.isTraveling.set(false);
          //Para que no esté desplegado el popup
          this.selectedCity.set(null);
          //dirige a la pantalla principal
          this.router.navigate(['/resume']);
        }, 1200); // 1.2s de animación
      },
      error: () => {
        this.isTraveling.set(false);
        alert('No se pudo completar el viaje');
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

  calculateDisplayedDamage(baseDamage: number): number {
    const hasGuardService = this.currentGame.activeServices().some(s => s.name.toLowerCase() === 'guardias');

    if (hasGuardService) {
      return Math.floor(baseDamage * 0.75);
    }
    return baseDamage;
  }


  applyTravelTime(originalTime: number): number {
    const speed = this.activeEffects().find(e => e.name.toLowerCase() === 'mejorar velocidad');
    if (speed) {
      const reduction = speed.improvementPerPurchase * speed.currentUpgrade;
      return Math.floor(originalTime * (1 - reduction));
    }
    return originalTime;
  }



}
