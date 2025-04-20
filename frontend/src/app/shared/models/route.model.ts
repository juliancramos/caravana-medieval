import { City } from './city.model';

export interface Route {
  idRoute: number;
  type: string;
  originCity: City;
  destinationCity: City;
  damage: number;
  damageCause: string;
  travelTime: number;
}
