import { Product } from "./product.model";

export interface ProductForStore {
  product: Product;
  quantity: number;
  price: number; 
}
