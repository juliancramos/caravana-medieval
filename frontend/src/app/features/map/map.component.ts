import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
@Component({
  selector: 'app-map',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent {
  constructor(private router: Router) {}
  // Ciudad actual (esto se conectará luego con el backend)
  currentCity = 'Eldenport';
  selectedCity: any = null;
  // Ciudades visibles en el mapa (solo para pruebas)
  cities = [
    {name: 'Eldenport', x: 20, y: 40, available: false},
    {name: 'Drakenshire', x: 30, y: 40, available: true},
    {name: 'Valenfort', x: 45, y: 60, available: true},
    {name: 'Norhollow', x: 25, y: 75, available: false},
    {name: 'Ironhill', x: 60, y: 20, available: true},
    {name: 'Stormwick', x: 75, y: 50, available: false},
    {name: 'Greyrock', x: 80, y: 70, available: false},
    {name: 'Shadowfen', x: 10, y: 40, available: false},
    {name: 'Duskreach', x: 55, y: 85, available: true},
    {name: 'Brightmere', x: 90, y: 35, available: false}
  ];

  selectCity(city: any): void {
    if (city.available) {
      console.log('Viajar a:', city.name);
      // Aquí luego se abrirá el popup con rutas disponibles
    }
  }

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

  travelTo(city: any): void {
    alert(`Viajando a ${city.name}`);
    this.closePopup();
  }

  goBack(): void {
    this.router.navigate(['/resume']);
  }
}
