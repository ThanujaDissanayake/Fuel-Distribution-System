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

  constructor(private httpService: HttpServiceService) { }

  subscriber!:Subscription;
  sheduledOrder!:ScheduledOrder[] ;
  filteredUsers:ScheduledOrder[]=[] ;
  isShow = false;
  referenceid:string='';
  getSheduedOrder?:ScheduledOrder;
  e:ScheduledOrder=new ScheduledOrder('','','','','',2);



  ngOnInit(): void {  
   /* this.subscriber=this.httpService.getOrderStatus().subscribe({
      next:data=>{
        this.sheduledOrder=data;
      }
    })*/
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

/* filterorders(referenceid:string) {
    //this.isShow=true;
  this.referenceid=referenceid;
  this.search()
  this.getSheduedOrder =this.sheduledOrder.find(e=>e.orderId==referenceid);
  console.log('filetr called', this.getSheduedOrder);
  }*/

}
