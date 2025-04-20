import { Route } from "./route.model";

export interface CityWithRoute {
  id: number;
  name: string;
  x: number;
  y: number;
  routes: Route[];
}
