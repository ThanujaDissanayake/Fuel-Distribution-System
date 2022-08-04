import { Observable } from 'rxjs';
import { Order } from './order.model';
import { Injectable } from '@angular/core';
import {HttpClient,HttpParams} from '@angular/common/http';
import { ScheduledOrder } from './scheduled-order.model';

@Injectable({
  providedIn: 'root'
})
export class HttpServiceService {

  //
  constructor(private http:HttpClient) { }

 /* placeOrder(Order:order){
    return this.http.post<Order>(this._url,order);
  }*/

 _searchUrl='http://localhost:8080/search';
  getOrderStatus(referenceid:string):Observable<any>{
    let param=new HttpParams().set('orderId',referenceid);
    return this.http.get(this._searchUrl,{params:param});
  }
}
