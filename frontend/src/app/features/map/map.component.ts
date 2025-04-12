import {Component, computed} from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { GameStateService } from '@core/services/game-state.service';
import {GameStatusBarComponent} from '@shared/game-status-bar/game-status-bar.component';
@Component({
  selector: 'app-map',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent {
  constructor(private gameState: GameStateService, private router: Router) {}

 currentCity : string = "Eldenport"
  selectedCity: any = null;

  // test cities
  cities = [
    { name: 'Eldenport', x: 20, y: 40, available: true },
    { name: 'Drakenshire', x: 30, y: 40, available: true },
    { name: 'Valenfort', x: 45, y: 60, available: true },
    { name: 'Norhollow', x: 25, y: 75, available: true },
    { name: 'Ironhill', x: 60, y: 20, available: true },
    { name: 'Stormwick', x: 75, y: 50, available: true },
    { name: 'Greyrock', x: 80, y: 70, available: true },
    { name: 'Shadowfen', x: 10, y: 40, available: true },
    { name: 'Duskreach', x: 55, y: 85, available: true },
    { name: 'Brightmere', x: 90, y: 35, available: true },

    // Nuevas ciudades
    { name: 'Frostgarde', x: 15, y: 10, available: true },
    { name: 'Sunspire', x: 25, y: 15, available: true },
    { name: 'Thornshade', x: 35, y: 25, available: true },
    { name: 'Ravenmoor', x: 45, y: 15, available: true },
    { name: 'Oakendale', x: 50, y: 30, available: true },
    { name: 'Ironwood', x: 65, y: 25, available: true },
    { name: 'Windscar', x: 75, y: 15, available: true },
    { name: 'Blackwater', x: 85, y: 25, available: true },
    { name: 'Ashenfield', x: 70, y: 35, available: true },
    { name: 'Briarholm', x: 60, y: 60, available: true },
   /* { name: 'Wyrmrest', x: 50, y: 70, available: true },
    { name: 'Highreach', x: 35, y: 85, available: true },
    { name: 'Mooncliff', x: 20, y: 90, available: true },
    { name: 'Emberfall', x: 10, y: 85, available: true },
    { name: 'Stonehaven', x: 5, y: 65, available: true },
    { name: 'Redpine', x: 15, y: 55, available: true },
    { name: 'Silverrest', x: 25, y: 50, available: true },
    { name: 'Stormhold', x: 40, y: 45, available: true },
    { name: 'Gladefort', x: 65, y: 80, available: true },
    { name: 'Whiteridge', x: 85, y: 60, available: true }*/
  ];



  openPopup(city: any): void {
    // Simular rutas (esto se conectará al backend más adelante)
    this.selectedCity = {
      ...city,
      routes: [
        {
          type: 'Ruta segura',
          time: '4h',
          damage: 0,
          cause: null
        },
        {
          type: 'Ruta segura',
          time: '4h',
          damage: 0,
          cause: null
        },
        {
          type: 'Ruta segura',
          time: '4h',
          damage: 0,
          cause: null
        },
        {
          type: 'Ruta segura',
          time: '4h',
          damage: 0,
          cause: null
        },
        {
          type: 'Ruta segura',
          time: '4h',
          damage: 0,
          cause: null
        },
        {
          type: 'Ruta segura',
          time: '4h',
          damage: 0,
          cause: null
        },

        {
          type: 'Ruta insegura',
          time: '2h',
          damage: 20,
          cause: 'Bandidos'
        }
      ]
    };
  }

  closePopup(): void {
    this.selectedCity = null;
  }


  goBack(): void {
    this.router.navigate(['/resume']);
  }


}
