import { data } from 'jquery';
import { ScheduledOrder } from './../common/scheduled-order.model';
import { Component, OnInit } from '@angular/core';
import {HttpServiceService} from '../common/http-service.service';
import { Subscription } from 'rxjs';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-status',
  templateUrl: './status.component.html',
  styleUrls: ['./status.component.scss']
})
export class StatusComponent implements OnInit {

  constructor(private httpService: HttpServiceService) { }

  subscriber!:Subscription;
  sheduledOrder!:ScheduledOrder[] ;
  isShow = false;
  referenceid:string='';
  getSheduedOrder!:ScheduledOrder;

  ngOnInit(): void {  
   /* this.subscriber=this.httpService.getOrderStatus().subscribe({
      next:data=>{
        this.sheduledOrder=data;
      }
    })*/
  }
 

 
  search(referenceid:string) {
   /*this.httpService.getOrderStatus(this.referenceid)
   .subscribe(
    data=>{
      this.getSheduedOrder=data;
    }
   )*/
    console.log('REFERENCE id', referenceid);
  }

}
