import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import {AppComponent} from "./app.component";
import {SuccessComponent} from "./success/success.component";
import {CancelComponent} from "./cancel/cancel.component";
import {HommeComponent} from "./homme/homme.component";


const routes: Routes = [


  {path: '' , redirectTo: '/home', pathMatch: 'full'},

  {path: 'home', component:HommeComponent},
  {path: 'suc', component:SuccessComponent},
  {path: 'can', component:CancelComponent}


];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
