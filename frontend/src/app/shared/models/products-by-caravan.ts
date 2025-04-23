import { Product } from './product.model';
import { Caravan } from './caravan.model';

export interface ProductsByCaravan {
  id: {
    caravanId: number;
    productId: number;
  };
  caravan: Caravan;
  product: Product;
  quantity: number;
}
