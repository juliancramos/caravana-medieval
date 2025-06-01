import { CommonModule } from '@angular/common';
import { ChangeDetectionStrategy, Component, EventEmitter, inject, Output, signal } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { InviteCodeService } from '@core/services/invitation-code.service';

@Component({
  selector: 'app-join-game-popup',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './join-game-popup.component.html',
  styleUrls: ['./join-game-popup.component.scss']
})
export class JoinGamePopupComponent {
  @Output() close = new EventEmitter<void>();

  private inviteService = inject(InviteCodeService);

  code: string = '';
  validated = signal(false);
  error = signal<string | null>(null);
  validatedGameId: number | null = null;

  validate(): void {
    this.error.set(null);
    this.validated.set(false);

    //para evitar consultas innecesarias en la base de datos
    if (this.code.length < 10) {
      this.error.set('Código muy corto.');
      return;
    }

    this.inviteService.validateCode(this.code).subscribe({
      next: (gameId) => {
        this.validatedGameId = gameId;
        //para que se muestre botón y mensaje de éxito
        this.validated.set(true);
      },
      error: () => {
        this.error.set('Código inválido o expirado.');
        this.validated.set(false);
      }
    });
  }

  joinGame(): void {
    if (this.validatedGameId != null) {
      // Aquí se hará la carga de la partida
    }
  }

  closePopup(): void {
    this.close.emit();
  }
}
