package org.example.testpay.Entities;

import jakarta.persistence.*;
import jdk.jfr.Enabled;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class Payment {

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
