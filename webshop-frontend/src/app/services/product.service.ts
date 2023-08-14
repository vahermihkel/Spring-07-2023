import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Product } from '../models/product.model';
// import { environment } from 'src/environment/environment';
import { environment } from '../../environment/environment';
import { Page } from '../models/page.interface';

@Injectable({
  providedIn: 'root'
})
export class ProductService {
  private url = environment.baseUrl + "/products";
  private productsPerPage = 2;

  constructor(private httpClient: HttpClient) { }

  addProduct(product: Product) {
    return this.httpClient.post(this.url, product);
  }

  deleteProduct(product: Product) {
    return this.httpClient.delete<Product[]>(this.url + "/" + product.id);
  }

  getProducts() {
    const options = this.getAuthToken();
    // let headers = new HttpHeaders();
    // headers = headers.append('Authorization', "Bearer " + sessionStorage.getItem("token"));

    return this.httpClient
      .get<Product[]>(this.url, options);
  }

  getPublicProducts(currentPage: number) {
    return this.httpClient
      .get<Page>(environment.baseUrl + `/public-products?page=${currentPage}&size=${this.productsPerPage}`);
  }

  getProduct(id: number) {
    return this.httpClient.get<Product>(this.url + "/" + id);
  }

  editProduct(product: Product) {
    return this.httpClient.put<void>(this.url  + "/" + product.id, product);
  }

  // decreaseStock(product: Product) {
  //   return this.httpClient.patch(environment.baseUrl + "/decrease-stock/" + product.id, {});
  // }

  decreaseStock(product: Product) {
    // console.log(id);
    return this.httpClient.patch<Product[]>(environment.baseUrl + "/decrease-stock/" + product.id, {}, this.getAuthToken());
  }

  increaseStock(product: Product) {
    return this.httpClient.patch<Product[]>(environment.baseUrl + "/increase-stock/" + product.id, {}, this.getAuthToken());
  }

  
  // increaseStock(id: Omit<Product, "id" | "name">) {
  //   return this.httpClient.patch(this.url, {});
  // }

  private getAuthToken() {
    return {
      headers: new HttpHeaders({
        "Authorization": "Bearer " + sessionStorage.getItem("token")
      })
    };
  }
}
