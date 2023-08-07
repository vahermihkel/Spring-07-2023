import { Injectable } from '@angular/core';
import { CartProduct } from '../models/cart-product.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private url = environment.baseUrl + "/orders/1"

  constructor(private httpClient: HttpClient) { }

  getPaymentLink(cartProducts: CartProduct[]) {  // ise võiks saata objekti 
    return this.httpClient.post(this.url, cartProducts, { responseType: 'text' });
  }
}
