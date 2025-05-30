import { Injectable, signal } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Router } from '@angular/router';
import { LoginRequest } from '@shared/models/login-request.model';
import { JwtAuthenticationResponse } from '@shared/models/jwt-authentication-response.model';
import {jwtDecode} from 'jwt-decode';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private userSignal = signal<JwtAuthenticationResponse | null>(null);

  constructor(private http: HttpClient, private router: Router) {
    const raw = sessionStorage.getItem('user');
    if (raw) this.userSignal.set(JSON.parse(raw));
  }

  login(request: LoginRequest) {
    return this.http.post<JwtAuthenticationResponse>('/api/auth/login', request);
  }

  setUser(user: JwtAuthenticationResponse) {
    const decoded: any = jwtDecode(user.token);
    user.role = decoded.role;
    user.idPlayer = decoded.idPlayer;
    this.userSignal.set(user);
    sessionStorage.setItem('user', JSON.stringify(user));
  }

  get user() {
    return this.userSignal.asReadonly();
  }

  getToken(): string | null {
    return this.userSignal()?.token || null;
  }

  logout() {
    this.userSignal.set(null);
    sessionStorage.removeItem('user');
    this.router.navigate(['/login']);
  }
}
