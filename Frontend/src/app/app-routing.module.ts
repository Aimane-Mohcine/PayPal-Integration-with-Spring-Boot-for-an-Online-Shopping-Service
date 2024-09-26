import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from "./app.component";
import {SuccessComponent} from "./success/success.component";
import {CancelComponent} from "./cancel/cancel.component";
import {HommeComponent} from "./homme/homme.component";
import {PayoutServiceComponent} from "./payout-service/payout-service.component";
import {PayoutStatusComponentComponent} from "./payout-status-component/payout-status-component.component";


const routes: Routes = [

  {path: '' , component: HommeComponent },

  {path: 'aa' , component: HommeComponent},

  {path: 'cc', component:PayoutServiceComponent},

  {path: 'home', component:HommeComponent},
  {path: 'suc', component:SuccessComponent},
  {path: 'can', component:CancelComponent},
  {path: 'send', component:PayoutServiceComponent},
  { path: 'payout-success', component: PayoutStatusComponentComponent },
  { path: 'payout-failure', component: PayoutStatusComponentComponent }



];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
