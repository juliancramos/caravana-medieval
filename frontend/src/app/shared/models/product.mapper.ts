// product.mapper.ts

import { ProductForStore } from "./product-for-store";
import { ProductWithQuantity } from "./product-with-quantity";
import { ProductsByCaravan } from "./products-by-caravan";
import { ProductsByCity } from "./products-by-city";

export class ProductMapper {
  static fromCaravanList(source: ProductsByCaravan[]): ProductWithQuantity[] {
    return source.map(p => ({
      product: p.product,
      quantity: p.quantity
    }));
  }

  static fromCityList(source: ProductsByCity[]): ProductForStore[] {
    return source.map(p => {
      const price = Math.round(p.supplyFactor / (1 + p.quantity));
      return {
        product: p.product,
        quantity: p.quantity,
        price
      };
    });
  }
}
