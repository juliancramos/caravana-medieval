
import { Product } from './product.model';

export interface ProductWithQuantity {
  product: Product;
  quantity: number;
}
