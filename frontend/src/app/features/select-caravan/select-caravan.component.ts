import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router} from '@angular/router';

@Component({
  selector: 'app-select-caravan',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './select-caravan.component.html',
  styleUrls: ['./select-caravan.component.scss']
})
export class SelectCaravanComponent {

  animateStats = false;
  animateImage = false;

  constructor( private router: Router) {}
  caravans = [
    {
      name: 'Caravana Básica',
      image: '/assets/caravans/basic-caravan.png',
      capacity: '100 unidades',
      speed: 'Media',
      defense: 'Baja',
      special: 'Ninguna'
    },
    {
      name: 'Caravana Blindada',
      image: '/assets/caravans/basic-caravan.png',
      capacity: '80 unidades',
      speed: 'Lenta',
      defense: 'Alta',
      special: 'Reducción de daño en rutas inseguras'
    },
    {
      name: 'Caravana Ligera',
      image: '/assets/caravans/basic-caravan.png',
      capacity: '60 unidades',
      speed: 'Alta',
      defense: 'Baja',
      special: 'Menor tiempo de viaje'
    }
  ];

  currentIndex = 0;

  get selectedCaravan() {
    return this.caravans[this.currentIndex];
  }


  selectCaravan(): void {
    const selected = this.caravans[this.currentIndex];
    console.log('Caravana seleccionada:', selected.name);


    this.router.navigate(['/seleccionar-partida']);
  }

  prevCaravan(): void {
    this.currentIndex = (this.currentIndex - 1 + this.caravans.length) % this.caravans.length;
    this.triggerAnimation();
  }

  nextCaravan(): void {
    this.currentIndex = (this.currentIndex + 1) % this.caravans.length;
    this.triggerAnimation();
  }

  triggerAnimation(): void {
    this.animateStats = false;
    this.animateImage = false;
    setTimeout(() => {
      this.animateStats = true;
      this.animateImage = true;
    }, 0);
  }

}
