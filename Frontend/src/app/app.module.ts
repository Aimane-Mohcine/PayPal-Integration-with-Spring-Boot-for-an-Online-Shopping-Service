import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { SuccessComponent } from './success/success.component';
import { CancelComponent } from './cancel/cancel.component';
import { HommeComponent } from './homme/homme.component';
import {HttpClientModule} from "@angular/common/http";
import { PayoutServiceComponent } from './payout-service/payout-service.component';
import {FormsModule} from "@angular/forms";
import { PayoutStatusComponentComponent } from './payout-status-component/payout-status-component.component';
import {RouterModule} from "@angular/router";

@NgModule({
  declarations: [
    AppComponent,
    SuccessComponent,
    CancelComponent,
    HommeComponent,
    PayoutServiceComponent,
    PayoutStatusComponentComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    RouterModule,
    FormsModule // Assurez-vous que FormsModule est importé ici


  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
