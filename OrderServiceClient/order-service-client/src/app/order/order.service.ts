import { Order } from './../common/order.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  _url='http://localhost:8083/home/orders';
  constructor(private _http:HttpClient) { }

  public placeOrder(order:Order){
      return this._http.post<any>(this._url,order);
  }
}
