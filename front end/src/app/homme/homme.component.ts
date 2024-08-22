import { Component, OnInit } from '@angular/core';
import { HttpClient } from "@angular/common/http";

@Component({
  selector: 'app-homme',
  templateUrl: './homme.component.html',
  styleUrls: ['./homme.component.css']  // Correction de 'styleUrl' à 'styleUrls'
})
export class HommeComponent implements OnInit {

  products = [
    {id: 1, name: 'Product 1', price: 0.01},
    {id: 2, name: 'Product 2', price: 20.00},
    {id: 3, name: 'Product 3', price: 30.00},
  ];

  constructor(private http: HttpClient) {}


  ngOnInit(): void {
  }


  buyProduct(productId: number, price: number): void {
    const currency = "USD";
    const description = "Achat de produit";
    const idOrder = productId.toString();  // Convertir l'ID du produit en chaîne de caractères

    console.log("idOrder being sent: " + idOrder);  // Ajouter un log pour vérifier la valeur envoyée

    this.http.post<any>('http://localhost:8081/api/pay/create', {
      amount: price,
      currency: currency,
      description: description,
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
