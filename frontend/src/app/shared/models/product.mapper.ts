// product.mapper.ts

import { ProductWithQuantity } from "./product-with-quantity";
import { ProductsByCaravan } from "./products-by-caravan";

export class ProductMapper {
  static fromCaravanList(source: ProductsByCaravan[]): ProductWithQuantity[] {
    return source.map(p => ({
      product: p.product,
      quantity: p.quantity
    }));
  }
}
