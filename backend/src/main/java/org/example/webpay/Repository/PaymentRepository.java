package org.example.webpay.Repository;

import org.example.webpay.Entities.ClientPayment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<ClientPayment, Integer> {
}
