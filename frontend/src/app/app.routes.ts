import { Routes } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { SelectGameComponent } from './features/select-game/select-game.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'seleccionar-partida', component: SelectGameComponent }
];
