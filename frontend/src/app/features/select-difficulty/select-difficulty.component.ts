import { Component, inject } from '@angular/core';
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
  private gameCreation = inject(GameCreationService);


  currentIndex = 0;

}
