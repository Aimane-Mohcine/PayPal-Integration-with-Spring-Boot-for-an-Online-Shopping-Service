import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";
import {Router} from "@angular/router";

@Component({
  selector: 'app-homme',
  templateUrl: './homme.component.html',
  styleUrls: ['./homme.component.css']  // Correction de 'styleUrl' à 'styleUrls'
})
export class HommeComponent implements OnInit {

  products = [
    {id: 1, name: 'Product 1', price: 10.00},
    {id: 2, name: 'Product 2', price: 20.00},
    {id: 3, name: 'Product 3', price: 30.00},
  ];

  constructor(private http: HttpClient, private router: Router) { }


  ngOnInit(): void {
  }


  buyProduct(productId: number, price: number): void {
    const idOrder = productId.toString();  // Convertir l'ID du produit en chaîne de caractères


    this.http.post<any>('http://localhost:8081/api/pay/create', {
      amount: price,
      idOrder: idOrder  // Inclure idOrder dans la requête
    }).subscribe(
      (data) => {
        if (data.success) {
          window.location.href = data.redirectUrl;
        } else if (data.message === "Payment cancelled") {
          window.location.href = "/cancel.html";
        } else {
          console.error('Payment failed:', data.message);
          alert('Payment failed: ' + data.message);
        }
      },
      (error) => {
        console.error('Error:', error);
        alert('An error occurred: ' + error.message);
      }
    );
  }



}
