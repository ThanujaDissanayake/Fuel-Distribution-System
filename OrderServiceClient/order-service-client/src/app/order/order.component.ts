import { Order } from './../common/order.model';
import { Component, OnInit } from '@angular/core';
import { FormGroup,FormControl } from '@angular/forms';
import {HttpServiceService} from '../common/http-service.service';
import * as $ from 'jquery';
import { OrderService } from './order.service';
import { data, error } from 'jquery';
import {NgForm} from '@angular/forms'

@Component({
  selector: 'app-order',
  templateUrl: './order.component.html',
  styleUrls: ['./order.component.scss']
})
export class OrderComponent implements OnInit {

  constructor( private _orderService: OrderService ) { }


  ngOnInit(): void {
  }

  alert:boolean=false;
  order=new Order('','',0);

 submit(orderform:NgForm){
    //console.log(this.order);
    this._orderService.placeOrder(this.order).subscribe(
      data=>console.log('Success !',data),
      error=>console.log('Error !',error)
    )
    this.alert=true;
    orderform.reset();
  }

  closeAlert(){
    this.alert=false;
  }



}
