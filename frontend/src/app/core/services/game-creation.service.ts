import { Injectable } from '@angular/core';
import { Caravan } from '@shared/models/caravan.model';
import { DifficultyOption } from '@shared/models/difficulty-option.model';

@Injectable({ providedIn: 'root' })
export class GameCreationService {
  private selectedCaravan: Caravan | null = null;
  private selectedDifficulty: DifficultyOption | null = null;

 public  setCaravan(caravan: Caravan) {
    this.selectedCaravan = caravan;
  }

  public getCaravan(): Caravan | null {
    return this.selectedCaravan;
  }

   public setDifficulty(difficulty: DifficultyOption) {
    this.selectedDifficulty = difficulty;
  }

   public getDifficulty(): DifficultyOption | null {
    return this.selectedDifficulty;
  }
}
