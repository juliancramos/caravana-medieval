import { BaseProductItem } from "./base-product-item";
import { Product } from "./product.model";

export interface ProductForStore extends BaseProductItem {
  price: number; 
}
