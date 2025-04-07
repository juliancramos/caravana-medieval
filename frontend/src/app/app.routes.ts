import { Routes } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { SelectGameComponent } from './features/select-game/select-game.component';
import { ResumeComponent } from './features/resume/resume.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'seleccionar-partida', component: SelectGameComponent },
  { path: 'resume', component: ResumeComponent }
];
