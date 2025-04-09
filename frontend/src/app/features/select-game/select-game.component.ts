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
  showPopup = false;
  fadingOut = false;
  showBlackScreen = false;

  constructor(private router: Router) {}

  openPopup(): void {
    this.showPopup = true;
  }

  closePopup(): void {
    this.showPopup = false;
  }

  startGame(): void {
    this.showBlackScreen = true;

    setTimeout(() => {
      this.router.navigate(['/resume']);
    }, 900); // un poco más que la duración del fadeIn
  }

  loadGame(): void {
    alert('Cargar partida');
  }
}
