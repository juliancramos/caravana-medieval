import { Routes } from '@angular/router';
import { LoginComponent } from './features/login/login.component';
import { SelectGameComponent } from './features/select-game/select-game.component';
import { ResumeComponent } from './features/resume/resume.component';
import {StoreProductsComponent} from './features/store-products/store-products.component';
import {StoreServicesComponent} from './features/store-services/store-services.component';
import {MapComponent} from './features/map/map.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'seleccionar-partida', component: SelectGameComponent },
  { path: 'productos', component: StoreProductsComponent },
  { path: 'servicios', component: StoreServicesComponent },
  { path: 'mapa', component: MapComponent },
  { path: 'resume', component: ResumeComponent }
];
