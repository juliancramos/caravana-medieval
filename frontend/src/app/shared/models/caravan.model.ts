import { City } from './city.model';
import { Product } from './product.model';

export interface Caravan {
  idCaravan: number;
  name: string;
  speed: number;
  maxCapacity: number;
  availableMoney: number;
  lifePoints: number;
  imgUrl: string | null;
  products: Product[];
  currentCity: City;
}
