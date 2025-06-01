import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, Output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-join-game-popup',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './join-game-popup.component.html',
  styleUrls: ['./join-game-popup.component.scss']
})
export class JoinGamePopupComponent {
  @Output() close = new EventEmitter<void>();

  code: string = '';
  validated = signal(false);
  error = signal<string | null>(null);

  validate(): void {
    if (this.code.length < 10) {
      this.error.set('Código muy corto.');
      this.validated.set(false);
    } else {
      // temporal, solo front
      this.error.set(null);
      this.validated.set(true);
    }
  }

  closePopup(): void {
    this.close.emit();
  }
}
