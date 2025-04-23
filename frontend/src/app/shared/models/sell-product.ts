import { BaseProductItem } from './base-product-item';
import { Product } from './product.model';

export interface SellProductDTO extends BaseProductItem{
  price: number;
}
