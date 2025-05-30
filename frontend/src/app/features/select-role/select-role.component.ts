import {Component, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import {JwtAuthenticationResponse} from '@shared/models/jwt-authentication-response.model';
import { AuthService } from '@core/services/auth.service';

@Component({
  selector: 'app-select-role',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './select-role.component.html',
  styleUrls: ['./select-role.component.scss']
})
export class SelectRoleComponent implements OnInit {
  selectedRole: 'CARAVANERO' | 'COMERCIANTE' | null = null;
  loading = false;
  returnTo: string = 'select-map'; // valor por defecto

  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService
  ) {}

  ngOnInit(): void {
    const param = this.route.snapshot.queryParamMap.get('returnTo');
    if (param) {
      this.returnTo = param;
    }
  }

  select(role: 'CARAVANERO' | 'COMERCIANTE') {
    this.selectedRole = role;
  }

  confirmRole() {
    if (!this.selectedRole) return;
    this.loading = true;

    this.http.put<JwtAuthenticationResponse>(`/api/player/update-role?role=${this.selectedRole}`, {}).subscribe({
      next: (response) => {
        this.authService.setUser(response);
        this.router.navigate([`/${this.returnTo}`]);
      },
      error: () => {
        alert('Error updating role');
        this.loading = false;
      }
    });
  }

}
