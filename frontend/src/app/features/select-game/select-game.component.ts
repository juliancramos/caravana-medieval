import { Component } from '@angular/core';
import { CommonModule } from '@angular/common'; // 👈 IMPORTANTE

@Component({
  selector: 'app-select-game',
  standalone: true,
  imports: [CommonModule], // 👈 AÑADIR CommonModule
  templateUrl: './select-game.component.html',
  styleUrls: ['./select-game.component.scss']
})
export class SelectGameComponent {
  showPopup = false;

  openPopup(): void {
    this.showPopup = true;
  }

  closePopup(): void {
    this.showPopup = false;
  }

  createGame(): void {
    alert('Crear partida');
  }

  loadGame(): void {
    alert('Cargar partida');
  }
}
