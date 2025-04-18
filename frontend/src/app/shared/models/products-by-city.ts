import { City } from './city.model';
import { Product } from './product.model';

export interface ProductsByCity {
  id: {
    cityId: number;
    productId: number;
  };
  city: City;
  product: Product;
  quantity: number;
  supplyFactor: number;
  demandFactor: number;
}
