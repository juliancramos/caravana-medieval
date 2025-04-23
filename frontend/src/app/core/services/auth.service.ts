import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { LoginRequest } from '../../shared/models/login-request.model';
import { LoginResponse } from '../../shared/models/login-response.model';
import { Router } from '@angular/router';

@Injectable({ providedIn: 'root' })
export class AuthService {

  //Señal reactiva, inicialmente en null ya que no hay sesión actica
  private userSignal = signal<LoginResponse | null>(null);

  //Http para solicitudes y router para redirigir a rutas
  constructor(private http: HttpClient, private router: Router) {
    //Verifica si hay una instancia, si la hay actualiza la señal
    const raw = localStorage.getItem('user');
    if (raw) this.userSignal.set(JSON.parse(raw));
  }

  login(request: LoginRequest) {
    return this.http.post<LoginResponse>('/api/auth/login', request);
  }

  setUser(user: LoginResponse) {
    this.userSignal.set(user);
    localStorage.setItem('user', JSON.stringify(user));
  }

  get user() {
    return this.userSignal.asReadonly();
  }

  logout() {
    this.userSignal.set(null);
    localStorage.removeItem('user');
    this.router.navigate(['/login']);
  }
}
