import { Component, OnInit, inject, signal } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
import { CaravanService } from '@core/services/caravan.service';
import { Caravan } from '@shared/models/caravan.model';
import { GameCreationService } from '@core/services/game-creation.service';
import { DifficultyOption } from '@shared/models/difficulty-option.model';

@Component({
  selector: 'app-select-caravan',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './select-caravan.component.html',
  styleUrls: ['./select-caravan.component.scss']
})
export class SelectCaravanComponent implements OnInit {
  private caravanService = inject(CaravanService);
  private gameCreation = inject(GameCreationService);
  private router = inject(Router);

  caravans = signal<Caravan[]>([]);
  currentIndex = 0;

  // Dificultades disponibles
  difficulties: DifficultyOption[] = [
    { level: 'Fácil', goalMoney: 5000, timeLimit: 50 },
    { level: 'Medio', goalMoney: 8000, timeLimit: 40 },
    { level: 'Difícil', goalMoney: 12000, timeLimit: 30 }
  ];
  difficultyIndex = 0;

  animateStats = false;
  animateImage = false;

  ngOnInit(): void {
    this.caravanService.getAllCaravans().subscribe({
      next: data => this.caravans.set(data),
      error: err => console.error('Error loading caravans', err)
    });
  }

  get selectedCaravan(): Caravan | null {
    return this.caravans()[this.currentIndex] ?? null;
  }

  get selectedDifficulty(): DifficultyOption {
    return this.difficulties[this.difficultyIndex];
  }

  prevCaravan(): void {
    const list = this.caravans();
    this.currentIndex = (this.currentIndex - 1 + list.length) % list.length;
    this.triggerAnimation();
  }

  nextCaravan(): void {
    const list = this.caravans();
    this.currentIndex = (this.currentIndex + 1) % list.length;
    this.triggerAnimation();
  }

  prevDifficulty(): void {
    this.difficultyIndex = (this.difficultyIndex - 1 + this.difficulties.length) % this.difficulties.length;
  }

  nextDifficulty(): void {
    this.difficultyIndex = (this.difficultyIndex + 1) % this.difficulties.length;
  }

  confirmSelection(): void {
    const caravan = this.selectedCaravan;
    if (!caravan) return;

    this.gameCreation.setCaravan(caravan);
    this.gameCreation.setDifficulty(this.selectedDifficulty);

    this.router.navigate(['/select-map']);
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
