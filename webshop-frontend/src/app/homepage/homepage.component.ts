import { Component } from '@angular/core';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'app-homepage',
  templateUrl: './homepage.component.html',
  styleUrls: ['./homepage.component.scss']
})
export class HomepageComponent {

  products = ["1", "2"];

  constructor(private httpClient: HttpClient) {} // Dependency Injection

  ngOnInit() { // reserveeritud funktsioon ComponentDidMount
    this.httpClient.get<any[]>("http://localhost:8080/products").subscribe((res: any[]) => 
      this.products = res
    );
  }

  addToCart(product: string) {
    // this.products = "LISASID OSTUKORVI";
  }
}
