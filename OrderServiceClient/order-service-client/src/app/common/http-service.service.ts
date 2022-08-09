import { Observable } from 'rxjs';
import { Order } from './order.model';
import { Injectable } from '@angular/core';
import {HttpClient,HttpParams} from '@angular/common/http';
import { ScheduledOrder } from './scheduled-order.model';

@Injectable({
  providedIn: 'root'
})
export class HttpServiceService {

  constructor(private http:HttpClient) { }

 _searchUrl='http://localhost:8083/home/allOrders';
 _scheduledAllUrl='http://localhost:8084/scheduledOrders/allOrders';
 _dispatchUrl='http://localhost:8084/scheduledOrders/dispatch';
 
  getOrderStatus():Observable<any>{
    return this.http.get<any>(this._searchUrl);
  }

  getScheduledAll():Observable<any>{
      return this.http.get<any>(this._scheduledAllUrl);
  }

  public dispatch(order:ScheduledOrder){
    return this.http.post<any>(this._dispatchUrl,order);
  }

}
