import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-select-game',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './select-game.component.html',
  styleUrls: ['./select-game.component.scss']
})
export class SelectGameComponent {
  constructor(private router: Router) {}

  loadGame(slot: number): void {
    this.router.navigate(['/resume']);
  }

  createNewGame(): void {
    this.router.navigate(['/crear-partida']);
  }

}
