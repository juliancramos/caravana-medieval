import { Injectable } from '@angular/core';
import { DifficultyOption } from '@shared/models/difficulty-option.model';

@Injectable({ providedIn: 'root' })
export class GameCreationService {
  private selectedDifficulty: DifficultyOption | null = null;

  setDifficulty(difficulty: DifficultyOption) {
    this.selectedDifficulty = difficulty;
  }

  getDifficulty(): DifficultyOption | null {
    return this.selectedDifficulty;
  }
}
