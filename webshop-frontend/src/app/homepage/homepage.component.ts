import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../models/product.model';
import { CartProduct } from '../models/cart-product.model';
import { ProductService } from '../services/product.service';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {
  products: Product[] = [];

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.productService.getProducts()
      .subscribe((data: Product[]) => {
        this.products = data;
      });
  }

  addToCart(productClicked: Product) {
    const cartItemsSS = sessionStorage.getItem("cartItems");
    let cartItems: CartProduct[] = [];
    if (cartItemsSS) {
      cartItems = JSON.parse(cartItemsSS);
    }                                                               
    const index = cartItems.findIndex(element => element.product.id === productClicked.id);
    if (index >= 0) {
      cartItems[index].quantity++; 
    } else {
      cartItems.push({ product: productClicked, quantity: 1 });
    }
    sessionStorage.setItem("cartItems", JSON.stringify(cartItems));
  }
}