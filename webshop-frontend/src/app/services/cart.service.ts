import { Injectable } from '@angular/core';
import { CartProduct } from '../models/cart-product.model';
import { HttpClient } from '@angular/common/http';
import { environment } from '../../environment/environment';

@Injectable({
  providedIn: 'root'
})
export class CartService {
  private paymentUrl = environment.baseUrl + "/orders/1";
  private parcelmachineUrl = environment.baseUrl + "/parcel-machines/"

  constructor(private httpClient: HttpClient) { }

  getPaymentLink(cartProducts: CartProduct[]) {  // ise võiks saata objekti 
    return this.httpClient.post(this.paymentUrl, cartProducts, { responseType: 'text' });
  }

  getParcelMachines() { // KOJU: EE , LV ja LT nupu kaudu pakiautomaadid 
    return this.httpClient.get<any>(this.parcelmachineUrl + "ee");
  }
}
