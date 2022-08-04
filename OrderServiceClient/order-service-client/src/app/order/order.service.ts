import { Order } from './../common/order.model';
import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, throwError } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class OrderService {

  _url='';
  constructor(private _http:HttpClient) { }

  placeOrder(order:Order){
      return this._http.post<any>(this._url,order);
  }
}
