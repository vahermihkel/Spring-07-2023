import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Product } from 'src/app/models/product.model';

@Component({
  selector: 'app-maintain-products',
  templateUrl: './maintain-products.component.html',
  styleUrls: ['./maintain-products.component.scss']
})
export class MaintainProductsComponent {

  products: Product[] = [];

  constructor(private httpClient: HttpClient) {} // Dependency Injection

  ngOnInit() { // reserveeritud funktsioon ComponentDidMount
    this.httpClient.get<Product[]>("http://localhost:8080/products").subscribe(res => 
      this.products = res
    );
  }
  
  deleteProduct(product: Product) {
    this.httpClient.delete<Product[]>("http://localhost:8080/products/" + product.id).subscribe(res => 
      this.products = res
    );
  }

  decreaseStock(product: Product) {
    
  }

  increaseStock(product: Product) {
    
  }

}
