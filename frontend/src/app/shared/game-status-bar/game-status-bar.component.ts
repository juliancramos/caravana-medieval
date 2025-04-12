import { Component, EventEmitter, Output } from '@angular/core';
import { CommonModule } from '@angular/common';
import { GameStateService } from '@core/services/game-state.service';

@Component({
  selector: 'app-game-status-bar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './game-status-bar.component.html',
  styleUrls: ['./game-status-bar.component.scss']
})
export class GameStatusBarComponent {
  constructor(public gameState: GameStateService) {}

  @Output() serviceSelected = new EventEmitter<any>();

  showServiceInfo(service: any) {
    this.serviceSelected.emit(service);
  }
}
