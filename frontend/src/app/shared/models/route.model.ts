import { City } from './city.model';

export interface Route {
  idRoute: number;
  type: string;
  originCity: City;
  destinationCity: City;
  damage: number;
  cause: string;
  travelTime: number;
}
