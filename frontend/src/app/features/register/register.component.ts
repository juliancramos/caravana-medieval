import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

import { AuthService } from '@core/services/auth.service';
import { LoginRequest } from '@shared/models/login-request.model';


@Component({
  selector: 'app-register',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.scss']
})
export class RegisterComponent {
  formData = {
    username: '',
    password: ''
  };

  error = '';

  constructor(
    private http: HttpClient,
    private authService: AuthService,
    private router: Router
  ) {}

  onSubmit(form: NgForm): void {
    if (form.invalid) return;

    const newPlayer = {
      username: this.formData.username,
      password: this.formData.password,
      role: 'USER',
      imgUrl: null
    };

    // 1. Crear jugador
    this.http.post('/api/player/create', newPlayer).subscribe({
      next: () => {
        // 2. Autologin
        const loginRequest: LoginRequest = {
          username: this.formData.username,
          password: this.formData.password
        };

        this.authService.login(loginRequest).subscribe({
          next: user => {
            this.authService.setUser(user);
            this.playLoginTransition();
          },
          error: () => {
            this.error = 'Usuario creado, pero error al iniciar sesión.';
          }
        });
      },
      error: () => {
        this.error = 'El nombre de usuario ya está en uso.';
      }
    });
  }

  playLoginTransition(): void {
    document.body.classList.add('fade-out');
    setTimeout(() => this.router.navigate(['/game-selection']), 800);
  }

}
