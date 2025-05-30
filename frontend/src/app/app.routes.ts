import { Routes } from '@angular/router';
import { LoginComponent } from '@features/login/login.component';
import { RegisterComponent } from '@features/register/register.component';
import { SelectMapComponent } from '@features/select-map/select-map.component';
import { ResumeComponent } from '@features/resume/resume.component';
import { StoreProductsComponent } from '@features/store-products/store-products.component';
import { StoreServicesComponent } from '@features/store-services/store-services.component';
import { ProductListComponent } from '@features/prueba/product-list.component';
import { MapComponent } from '@features/map/map.component';
import { SelectGameComponent } from '@features/select-game/select-game.component';
import { SelectCaravanComponent } from '@features/select-caravan/select-caravan.component';
import { InventoryComponent } from '@features/inventory/inventory.component';
import { SelectDifficultyComponent } from '@features/select-difficulty/select-difficulty.component';
import { AuthGuard } from './core/guards/auth.guard';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },

  // Public Routes
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },

  // Protected Routes
  { path: 'resume', component: ResumeComponent, canActivate: [AuthGuard] },
  { path: 'select-map', component: SelectMapComponent, canActivate: [AuthGuard] },
  { path: 'productos', component: StoreProductsComponent, canActivate: [AuthGuard] },
  { path: 'servicios', component: StoreServicesComponent, canActivate: [AuthGuard] },
  { path: 'prueba', component: ProductListComponent, canActivate: [AuthGuard] },
  { path: 'select-game', component: SelectGameComponent, canActivate: [AuthGuard] },
  { path: 'select-caravan', component: SelectCaravanComponent, canActivate: [AuthGuard] },
  { path: 'inventory', component: InventoryComponent, canActivate: [AuthGuard] },
  { path: 'select-difficulty', component: SelectDifficultyComponent, canActivate: [AuthGuard] },
  { path: 'mapa', component: MapComponent, canActivate: [AuthGuard] }
];
