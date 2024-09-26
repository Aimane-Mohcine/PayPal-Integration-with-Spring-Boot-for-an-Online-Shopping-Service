package org.example.webpay.Repository;

import org.example.webpay.Entities.SellerPayment;
import org.springframework.data.repository.CrudRepository;

public interface SellerPaymentRepository extends CrudRepository<SellerPayment, Integer> {
}

