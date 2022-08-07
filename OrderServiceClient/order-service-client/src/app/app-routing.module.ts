import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { CtcComponent } from './ctc/ctc.component';

const routes: Routes = [
  {path: 'ctc',component:CtcComponent}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
