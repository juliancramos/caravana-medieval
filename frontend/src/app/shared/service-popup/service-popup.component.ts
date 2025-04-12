import { Component, Input, Output, EventEmitter } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-service-popup',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './service-popup.component.html',
  styleUrls: ['./service-popup.component.scss']
})
export class ServicePopupComponent {
  @Input() service: any;
  @Output() close = new EventEmitter<void>();

  closePopup() {
    this.close.emit();
  }
}
