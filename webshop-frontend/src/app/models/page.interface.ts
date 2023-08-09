import { Product } from "./product.model";

export interface Page {
  content: Product[],
  totalElements: number,
  totalPages: number,
  number: number
}