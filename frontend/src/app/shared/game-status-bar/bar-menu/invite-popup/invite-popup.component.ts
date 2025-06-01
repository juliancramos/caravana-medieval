import { Component, Output, EventEmitter, signal } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-invite-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './invite-popup.component.html',
  styleUrls: ['./invite-popup.component.scss']
})
export class InvitePopupComponent {
  @Output() close = new EventEmitter<void>();

  // solo para prueba
  inviteCode = signal('123');

  copied = signal(false);
  //cuando se copia el codigo muestra un popup
  copyToClipboard(): void {
    navigator.clipboard.writeText(this.inviteCode()).then(() => {
      this.copied.set(true);
      setTimeout(() => this.copied.set(false), 2000);
    });
  }


  closePopup(): void {
    this.close.emit();
  }
}
