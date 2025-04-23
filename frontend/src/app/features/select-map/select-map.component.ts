import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-select-game',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './select-map.component.html',
  styleUrls: ['./select-map.component.scss']
})
export class SelectMapComponent {



  constructor(private router: Router) {}


  startGame(): void {
    this.router.navigate(['/resume']);
  }


}
