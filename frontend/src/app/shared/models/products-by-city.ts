import { City } from './city.model';
import { Product } from './product.model';

export interface ProductsByCity {
  id: {
    cityId: number;
    productId: number;
  };
  city: City;
  product: Product;
  cantidad: number;
  supplyFactor: number;
  demandFactor: number;
}
