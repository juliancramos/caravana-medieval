import {Component, inject} from '@angular/core';
import { CommonModule } from '@angular/common';
import { DifficultyOption } from '@shared/models/difficulty-option.model';
import { Router } from '@angular/router';
import { GameCreationService } from '@core/services/game-creation.service';

@Component({
  selector: 'app-select-difficulty',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './select-difficulty.component.html',
  styleUrls: ['./select-difficulty.component.scss']
})
export class SelectDifficultyComponent {

  private router = inject(Router);



  difficulties: DifficultyOption[] = [
    { level: 'Fácil', goalMoney: 5000, timeLimit: 50, maxCapacity: 120, initialLife: 100 },
    { level: 'Medio', goalMoney: 8000, timeLimit: 40, maxCapacity: 100, initialLife: 80 },
    { level: 'Difícil', goalMoney: 12000, timeLimit: 30, maxCapacity: 80, initialLife: 60 }
  ];

  currentIndex = 0;

  get currentDifficulty() {
    return this.difficulties[this.currentIndex];
  }

  prevDifficulty() {
    this.currentIndex = (this.currentIndex - 1 + this.difficulties.length) % this.difficulties.length;
  }

  nextDifficulty() {
    this.currentIndex = (this.currentIndex + 1) % this.difficulties.length;
  }

  confirmSelection() {
    console.log('Selected Difficulty:', this.currentDifficulty);
    // Aquí va la lógica para crear la partida en el backend
    this.router.navigate(['/seleccionar-partida']);
  }
}
