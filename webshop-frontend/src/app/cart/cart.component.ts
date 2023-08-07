import { Component } from '@angular/core';
import { CartProduct } from '../models/cart-product.model';

@Component({
  selector: 'app-cart',
  templateUrl: './cart.component.html',
  styleUrls: ['./cart.component.scss']
})
export class CartComponent {
  cartProducts: CartProduct[] = [];
  parcelmachines: any[] = [];
  sumOfCart = 0;

  ngOnInit(): void {
    const cartItemsSS = sessionStorage.getItem("cartItems");
    if (cartItemsSS) {
      this.cartProducts = JSON.parse(cartItemsSS); 
    }
    this.calculateSumOfCart();
  }

  onDecreaseQuantity(cartProduct: CartProduct) {
    cartProduct.quantity--;
    if (cartProduct.quantity <= 0) {
      this.onRemoveProduct(cartProduct);
    }
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.calculateSumOfCart();
  }

  onIncreaseQuantity(cartProduct: CartProduct) {
    cartProduct.quantity++;
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.calculateSumOfCart();
  }

  onRemoveProduct(cartProduct: CartProduct) {
    const index = this.cartProducts.findIndex(element => element.product.id === cartProduct.product.id);
    if (index >= 0) {
      this.cartProducts.splice(index,1);
      sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    }
    this.calculateSumOfCart();
  }

  onEmptyCart() {
    this.cartProducts = [];
    sessionStorage.setItem("cartItems", JSON.stringify(this.cartProducts));
    this.calculateSumOfCart();
  }

  calculateSumOfCart() {
    this.sumOfCart = 0;
    this.cartProducts.forEach(element => this.sumOfCart += element.product.price * element.quantity);
  }

  onPay() {
    // this.paymentService.getPaymentLink(this.cartProducts).subscribe(res => {
    //   window.location.href = res.payment_link;
    // });
  }
}
