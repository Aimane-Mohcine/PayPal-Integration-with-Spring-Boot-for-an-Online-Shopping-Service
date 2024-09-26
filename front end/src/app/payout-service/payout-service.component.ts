import { Component } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Router} from "@angular/router";

@Component({
  selector: 'app-payout-service',
  templateUrl: './payout-service.component.html',
  styleUrl: './payout-service.component.css'
})
export class PayoutServiceComponent {


  private apiUrl = 'http://localhost:8081/api/payout'; // L'URL de votre API Spring Boot

  recipientEmail: string = '';
  amount: string = '';
   sellers = [
    {id: '1', name: 'Seller 1'},
    {id: '2', name: 'Seller 2'},
    {id: '3', name: 'Seller 3'}
  ];
  constructor(private http: HttpClient, private router: Router) { }

  onSubmit() {
    const payload = {
      recipientEmail: this.recipientEmail,
      amount: this.amount,
      sellerId: this.sellers,
      email:this.recipientEmail
    };
// Liste des sellerId codée en dur


    this.http.post('http://localhost:8081/api/payout/send', payload).subscribe(
      response => {
        this.router.navigate(['/payout-success']);
      },
      error => {
        this.router.navigate(['/payout-failure']);
      }
    );
  }
}
