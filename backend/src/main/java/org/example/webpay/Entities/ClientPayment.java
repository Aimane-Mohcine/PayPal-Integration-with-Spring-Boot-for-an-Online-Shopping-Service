package org.example.webpay.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class ClientPayment  {

  @Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
private int idPayment;
  private  int idOrder;
  private Date Date_Payment;
  private Double Amount_Payment;
  private  String Payment_methode ;
  private String status;

  public void  setDate(){

    this.Date_Payment=new Date();
  }
}
