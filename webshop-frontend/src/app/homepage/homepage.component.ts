import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Product } from '../models/product.model';
import { CartProduct } from '../models/cart-product.model';
import { ProductService } from '../services/product.service';
import { Page } from '../models/page.interface';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {
  products: Product[] = [];
  pages: number[] = []; // [1,2]
  activePage = 1;

  constructor(private productService: ProductService) {}

  ngOnInit() {
    this.productService.getPublicProducts(0)
      .subscribe((data: Page) => {
        this.products = data.content;
        for (let index = 1; index <= data.totalPages; index++) {
          this.pages.push(index);
        }
      });
  }

  onChangePage(page: number) {
    this.activePage = page;
    this.productService.getPublicProducts(page - 1)
      .subscribe((data: Page) => {
        this.products = data.content;
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