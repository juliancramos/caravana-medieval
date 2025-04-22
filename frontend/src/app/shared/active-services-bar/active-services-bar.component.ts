import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-active-services-bar',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './active-services-bar.component.html',
  styleUrls: ['./active-services-bar.component.scss']
})
export class ActiveServicesBarComponent {
  @Input() activeServices: any[] = [];
  @Output() serviceSelected = new EventEmitter<any>();

  selectService(service: any) {
    this.serviceSelected.emit(service);
  }


}
