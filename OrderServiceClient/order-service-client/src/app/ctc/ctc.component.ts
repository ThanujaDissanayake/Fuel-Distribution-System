import { DispatchConfirmComponent } from './../dispatch-confirm/dispatch-confirm.component';
import { data, error } from 'jquery';
import { HttpServiceService } from './../common/http-service.service';
import { ScheduledOrder } from './../common/scheduled-order.model';
import { Component, OnInit } from '@angular/core';
import {MatDialog} from '@angular/material/dialog';

@Component({
  selector: 'app-ctc',
  templateUrl: './ctc.component.html',
  styleUrls: ['./ctc.component.scss']
})
export class CtcComponent implements OnInit {

  scheduledOrders!:ScheduledOrder[];
  isDispathed:boolean=false;


  constructor(private httpService:HttpServiceService) { }

  ngOnInit() {
    this.httpService.getScheduledAll().subscribe(data=> {
      this.scheduledOrders=data;
    })
  }

  dispatchOrder(order:any){
    order.status='DISPATCHED';
    this.httpService.dispatch(order).subscribe(
      data=>console.log('Success !',data),
      error=>console.log('Error !',error)
    )
  }

}
