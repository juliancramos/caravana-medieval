import { ProductForStore } from "./product-for-store";
import { ProductsByCaravan } from "./products-by-caravan";
import { ProductsByCity } from "./products-by-city";
import { SellProductDTO } from "./sell-product";

export class ProductMapper {
 

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

  static fromCaravanWithPrices(source: ProductsByCaravan[], cityData: ProductsByCity[]): SellProductDTO[] {
    return source.map(p => {
      const cityMatch = cityData.find(c => c.product.idProduct === p.product.idProduct);
      const demandFactor = cityMatch?.demandFactor ?? 100;
      const stock = cityMatch?.quantity ?? 0;
      const price = Math.round(demandFactor / (1 + stock));

      return {
        product: p.product,
        quantity: p.quantity,
        price
      };
    });
  }
}
