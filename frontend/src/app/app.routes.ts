import { Routes } from '@angular/router';
import { LoginComponent } from '@features/login/login.component';
import { SelectMapComponent } from '@features/select-map/select-map.component';
import { ResumeComponent } from '@features/resume/resume.component';
import { StoreProductsComponent } from '@features/store-products/store-products.component';
import { StoreServicesComponent } from '@features/store-services/store-services.component';
import { ProductListComponent } from '@features/prueba/product-list.component';
import { MapComponent } from '@features/map/map.component';
import {SelectGameComponent} from '@features/select-game/select-game.component';
import {SelectCaravanComponent} from '@features/select-caravan/select-caravan.component';
import {InventoryComponent} from '@features/inventory/inventory.component';

export const routes: Routes = [
  { path: '', redirectTo: 'login', pathMatch: 'full' },
  { path: 'login', component: LoginComponent },
  { path: 'seleccionar-partida', component: SelectMapComponent },
  { path: 'productos', component: StoreProductsComponent },
  { path: 'servicios', component: StoreServicesComponent },
  { path: 'resume', component: ResumeComponent },
  { path: 'prueba', component: ProductListComponent },
  { path: 'select-game', component: SelectGameComponent },
  { path: 'select-caravan', component: SelectCaravanComponent },
  { path: 'inventory', component: InventoryComponent },
  { path: 'mapa', component: MapComponent }
];
