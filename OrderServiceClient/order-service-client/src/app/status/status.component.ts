import { DialogService } from './../common/dialog.service';
import { data } from 'jquery';
import { ScheduledOrder } from './../common/scheduled-order.model';
import { Component, OnInit } from '@angular/core';
import {HttpServiceService} from '../common/http-service.service';
import { scheduled, Subscription } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss']
})
export class StatusComponent implements OnInit {

  constructor( private httpService: HttpServiceService) { }

  subscriber!:Subscription;
  sheduledOrder!:ScheduledOrder[] ;
  filteredUsers:ScheduledOrder[]=[] ;
  isShow = false;
  referenceid:string='';
  getSheduedOrder?:ScheduledOrder;
  e:ScheduledOrder=new ScheduledOrder('','','','','',2);

  ngOnInit(): void { 

  }
 
  search(referenceid:string) {
    this.referenceid=referenceid;
   this.httpService.getOrderStatus()
   .subscribe(
    data=>{
      this.sheduledOrder=data;
      this.getSheduedOrder=this.sheduledOrder.find(e=>e.orderId==this.referenceid);
      if(this.getSheduedOrder!== undefined){
        this.isShow=true;
      }
      console.log(this.getSheduedOrder);
    }
   )
  }
}
