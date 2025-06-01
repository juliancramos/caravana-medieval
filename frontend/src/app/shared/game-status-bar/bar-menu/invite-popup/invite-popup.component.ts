import { Component, Output, EventEmitter, signal, inject } from '@angular/core';
import { CommonModule } from '@angular/common';
import { InviteCodeService } from '@core/services/invitation-code.service';
import { CurrentGameService } from '@core/services/current-game.service';

@Component({
  selector: 'app-invite-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './invite-popup.component.html',
  styleUrls: ['./invite-popup.component.scss']
})
export class InvitePopupComponent {

  @Output() close = new EventEmitter<void>();

  private inviteService = inject(InviteCodeService);
  private currentGame = inject(CurrentGameService);

  //trae el codigo generado desde el servicio
  inviteCode = this.inviteService.getInviteCodeSignal();
  //para popup 
  copied = signal(false);

  //apenas se inicia el componente pide el id de invitacion aleatorio
  constructor() {
    const idGame = this.currentGame.selectedGame()?.game.idGame;
    if (idGame != null) {
      this.inviteService.fetchInviteCode(idGame).subscribe({
        //actualiza la señal cuando llega el codigo
        next: (code) => this.inviteService.setInviteCode(code),
        error: (err) => console.error('Failed to fetch invite code', err),
      });
    }
  }

  

  //cuando se copia el codigo muestra un popup
  copyToClipboard(): void {
    if (!this.inviteCode()) return;

    navigator.clipboard.writeText(this.inviteCode()!.code).then(() => {
      this.copied.set(true);
      setTimeout(() => this.copied.set(false), 2000);
    });
  }


  closePopup(): void {
    this.close.emit();
  }
}
