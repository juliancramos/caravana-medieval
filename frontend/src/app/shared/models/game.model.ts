import { Caravan } from './caravan.model';
import { MapData } from './map.model';

export interface Game {
  idGame: number;
  elapsedTime: number;
  timeLimit: number;
  minProfit: number;
  caravan: Caravan;
  map: MapData;
}
