import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss']
})
export class LoginComponent {
  fadingOut = false;
  showBlackScreen = false;

  constructor(private router: Router) {}

  navigateToGameSelection(): void {
    this.fadingOut = true;
    this.showBlackScreen = true;

    setTimeout(() => {
      this.router.navigate(['/seleccionar-partida']);
    }, 900);
  }
}
