import { Component, signal } from '@angular/core';
import { FormBuilder, ReactiveFormsModule, Validators } from '@angular/forms';
import { AuthService } from '../../core/services/auth.service';
import { Router } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoginRequest } from '@shared/models/login-request.model';

@Component({
  selector: 'login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.scss'],
  imports: [
    CommonModule,           // para ngIf, ngClass
    ReactiveFormsModule     // para formGroup, formControlName
  ]
})
export class LoginComponent {
  fadingOut = false;
  showBlackScreen = false;

  error = signal<string | null>(null);

  form;

constructor(private fb: FormBuilder, private authService: AuthService, private router: Router) {
  this.form = this.fb.group({
    username: ['', Validators.required],
    password: ['', Validators.required]
  });
}



  login() {
    if (this.form.invalid) return;

    this.authService.login(this.form.value as LoginRequest).subscribe({
      next: user => {
        this.authService.setUser(user);
        this.playLoginTransition();
      },
      error: () => {
        this.error.set('Invalid username or password');
      }
    });

  }

  playLoginTransition() {
    this.fadingOut = true;
    setTimeout(() => this.showBlackScreen = true, 600);
    setTimeout(() => this.router.navigate(['/select-game']), 1500);
  }

  createAccount() {
    this.router.navigate(['/register'])
  }
}
