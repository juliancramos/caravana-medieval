import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { HttpClient } from '@angular/common/http';

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
  success = '';

  constructor(
    private http: HttpClient,
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

    this.http.post('/api/player/create', newPlayer).subscribe({
      next: () => {
        this.success = '¡Usuario creado correctamente! Redirigiendo al login...';
        this.error = '';

        setTimeout(() => {
          this.router.navigate(['/login']);
        }, 1500);
      },
      error: () => {
        this.success = '';
        this.error = 'El nombre de usuario ya está en uso.';
      }
    });
  }
}
