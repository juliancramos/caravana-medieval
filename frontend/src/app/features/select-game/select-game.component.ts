import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import {Router} from '@angular/router'; // 👈 IMPORTANTE

@Component({
  selector: 'app-select-game',
  standalone: true,
  imports: [CommonModule], // 👈 AÑADIR CommonModule
  templateUrl: './select-game.component.html',
  styleUrls: ['./select-game.component.scss']
})
export class SelectGameComponent {
  constructor(private router: Router) {}
  showPopup = false;

  openPopup(): void {
    this.showPopup = true;
  }

  closePopup(): void {
    this.showPopup = false;
  }

  createGame(): void {
    this.router.navigate(['/resume']);
  }

  loadGame(): void {
    alert('Cargar partida');
  }
}
