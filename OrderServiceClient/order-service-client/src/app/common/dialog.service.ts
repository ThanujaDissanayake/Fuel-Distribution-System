import { Injectable } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { DispatchConfirmComponent } from '../dispatch-confirm/dispatch-confirm.component';

@Injectable({
  providedIn: 'root'
})
export class DialogService {

  constructor(private dialog:MatDialog) { }

  openConfirmDialog(){
    this.dialog.open(DispatchConfirmComponent, {
      width: '250px',
      disableClose:true
    });
  }
}
