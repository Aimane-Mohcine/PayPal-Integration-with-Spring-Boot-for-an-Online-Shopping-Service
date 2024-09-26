package org.example.webpay.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.Date;

@Entity
@Data
public class SellerPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPayment;
    private Integer sellerId;
    private Double amount;
    private String paymentStatus;
    private Date paymentDate;
    private  String email;


    public void  setDate(){

        this.paymentDate =new Date();
    }
}
