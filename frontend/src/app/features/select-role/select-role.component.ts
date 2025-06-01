import {Component, inject, OnInit} from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClient } from '@angular/common/http';
import { ActivatedRoute, Router } from '@angular/router';
import {JwtAuthenticationResponse} from '@shared/models/jwt-authentication-response.model';
import { AuthService } from '@core/services/auth.service';
import { GameStateService } from '@core/services/game-state.service';
import { CurrentGameService } from '@core/services/current-game.service';
import { Game } from '@shared/models/game.model';
import { GameByPlayerService } from '@core/services/game-by-player.service';

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
  //para saber si viene desde "unirse a una partida" o desde "crear una partida"
  fromPopup = false;
  //partida a la que se quiere unir el jugador
  gameId: number | null = null;
  //roles que ya han sido tomados por otros jugadores
  takenRoles: string[] = [];


  constructor(
    private http: HttpClient,
    private router: Router,
    private route: ActivatedRoute,
    private authService: AuthService,
    private GameStateService: GameStateService,
    private currentGame: CurrentGameService,
    private gameService: GameByPlayerService

  ) {}

  ngOnInit(): void {
    //busca si hay un parámetro de consulta 'returnTo' para saber a dónde redirigir al usuario después de seleccionar el rol
    //si no hay, por defecto redirige a 'select-map'
    const param = this.route.snapshot.queryParamMap.get('returnTo');
    if (param) {
      this.returnTo = param;
    }

    const fromPopupParam = this.route.snapshot.queryParamMap.get('fromPopup');
    const gameIdParam = this.route.snapshot.queryParamMap.get('gameId');
    //verifica si viene desde el popup de unirse a una partida
    this.fromPopup = fromPopupParam === 'true';

    if (this.fromPopup && gameIdParam) {
      //convierne a numero entero el gameId
      this.gameId = parseInt(gameIdParam, 10);
      if (!isNaN(this.gameId)) {
        this.loadTakenRoles(this.gameId);
      }
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

        // si viene desde el popup es porque se va a unir a una partida existente
        if (this.fromPopup && this.gameId !== null) {
          // primero se asigna el jugador a la partida
          this.gameService.assignMeToGame(this.gameId).subscribe({
            next: () => {
              // establecer estado inicial del juego
              this.currentGame.selectedGame.set({ game: { idGame: this.gameId } as Game });

              // refrescar los datos del juego
              this.currentGame.refreshGame(this.gameId!!);

              // redigir al usuario a pantalla principal de la partida
              this.router.navigate([`/${this.returnTo}`]);
            },
            error: err => {
              console.error('Error asignando jugador a la partida', err);
              this.loading = false;
            }
          });
        } else {
          // Si no viene del popup, sigue flujo normal (crear partida)
          this.router.navigate([`/${this.returnTo}`]);
        }
      },
      error: () => {
        alert('Error updating role');
        this.loading = false;
      }
    });
  }


  loadTakenRoles(gameId: number): void {
    this.GameStateService.getTakenRoles(gameId).subscribe({
    next: (roles) => this.takenRoles = roles,
    error: () => {
      console.error('Error loading roles from game');
      this.takenRoles = [];
    }
  });

    
  }



}
