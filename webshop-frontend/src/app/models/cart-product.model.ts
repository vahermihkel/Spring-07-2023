import { Product } from "./product.model";

export class CartProduct {
    constructor(
       public product: Product,
       public quantity: number
    ) {}
}