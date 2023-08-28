import { Injectable } from '@angular/core';
import { environment } from '../../environment/environment';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class TimeService {
  private url = environment.baseUrl + "/times";

  constructor(private httpClient: HttpClient) { }

  getTimes() {
    return this.httpClient.get<any>(this.url);
  }

}
