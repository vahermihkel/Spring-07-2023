import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { environment } from 'src/environment/environment';
import { AuthToken } from '../models/auth-token';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private url = environment.baseUrl;

  constructor(private httpClient: HttpClient) {}

  login(loginFormData: any) {
    const response = this.httpClient.post<AuthToken>(`${this.url}/login`, loginFormData);
    return response;
  }

  signUp(signUpFormData: any) {
    const response = this.httpClient.post(`${this.url}/signup`, signUpFormData);
    return response;
  }

  logout() {
    sessionStorage.removeItem("token");
    sessionStorage.removeItem("expiration");
  }
}
