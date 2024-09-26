package org.example.webpay.web;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.example.webpay.Entities.SellerPayment;
import org.example.webpay.Repository.PaymentRepository;
import org.example.webpay.Repository.SellerPaymentRepository;
import org.example.webpay.service.PaypalServiceClient;
import org.example.webpay.service.PaypalServiceSeller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.view.RedirectView;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@CrossOrigin
public class PayController {


    private final PaypalServiceClient paypalServiceClient;

    private org.example.webpay.Entities.ClientPayment obj;
    @Autowired
    PaymentRepository paymentClientRepository;
    @Autowired
    private PaypalServiceSeller paypalServiceSeller;

    @PostMapping("/pay/create")
    public Map<String, Object> createPayment(@RequestBody Map<String, String> request) {
        try {
            obj = new org.example.webpay.Entities.ClientPayment();
            Double amount = Double.parseDouble(request.get("amount"));
            String currency = "USD";
            String description = "je veut a cheter ";
            String idOrder = request.get("idOrder");  // Récupérer l'idOrder de la requête

            if (idOrder == null || idOrder.isEmpty()) {
                throw new IllegalArgumentException("idOrder is required and cannot be null or empty");
            }

            // Création du paiement PayPal
            Payment payment = paypalServiceClient.createPayment(
                    amount,
                    currency,
                    "paypal",
                    "sale",
                    description,
                    "http://localhost:8081/api/pay/cancel",  // Ajouter idOrder à l'URL d'annulation
                    "http://localhost:8081/api/pay/success?idOrder=" + idOrder    // Ajouter idOrder à l'URL de succès
            );

            String redirectUrl = payment.getLinks().stream()
                    .filter(link -> "approval_url".equals(link.getRel()))
                    .findFirst()
                    .map(link -> link.getHref())
                    .orElse("");

            // Pour la base de données (optionnel)
            obj.setDate();
            obj.setAmount_Payment(amount);
            obj.setPayment_methode("paypal");
            obj.setIdOrder(Integer.parseInt(idOrder));  // Conversion en entier si idOrder est valide

            return Map.of("success", true, "redirectUrl", redirectUrl);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            return Map.of("success", false, "message", "Invalid idOrder format");
        } catch (PayPalRESTException e) {
            e.printStackTrace();
            return Map.of("success", false, "message", e.getMessage());
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return Map.of("success", false, "message", e.getMessage());
        }
    }


    @GetMapping("/pay/success")
    public RedirectView successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("idOrder") String idOrder) {
        try {
            Payment payment = paypalServiceClient.executePayment(paymentId, payerId);
            if ("approved".equals(payment.getState())) {
                // Redirige vers l'URL de succès avec l'idOrder
                obj.setStatus("successful");
                paymentClientRepository.save(obj);
                return new RedirectView("http://localhost:4200/suc");
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        // En cas d'échec, redirige vers l'URL d'annulation avec l'idOrder
        obj.setStatus("failed");
        paymentClientRepository.save(obj);
        return new RedirectView("http://localhost:4200/can");
    }

    @GetMapping("/pay/cancel")
    public RedirectView cancelPay() {
        obj.setStatus("failed");
        paymentClientRepository.save(obj);
        return new RedirectView("http://localhost:4200/can");
    }

    // c est la partie de PaypalServiceSeller --------------------------------------------------------------


    private final PaypalServiceSeller paypalPayoutService;
    @Autowired
    SellerPaymentRepository sellerPaymentRepository;

    SellerPayment seller;

    @PostMapping("/payout/send")
    public ResponseEntity<Map<String, String>> sendPayout(@RequestBody Map<String, String> request) {
        String recipientEmail = request.get("recipientEmail");
        String amount = request.get("amount");
        String currency = "USD"; // Ou tout autre devise souhaitée
        String note = "Payout";
        String email = request.get("email");
        Integer sellerId = Integer.parseInt(request.get("sellerId"));

        seller = new SellerPayment();
        seller.setAmount(Double.parseDouble(amount));
        seller.setSellerId(sellerId);
        seller.setDate();
        seller.setEmail(email);

        Map<String, String> response = new HashMap<>();
        try {
            paypalServiceSeller.sendPayout(recipientEmail, amount, currency, note);
            // Si aucun exception n'est levée, on considère que le paiement a été envoyé avec succès
            response.put("status", "success");
            response.put("message", "Payout sent successfully.");
            seller.setPaymentStatus("Payout sent successfully");
            sellerPaymentRepository.save(seller);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            // Si une exception est levée, on considère que le paiement a échoué
            response.put("status", "failure");
            response.put("message", "Payout failed.");
            seller.setPaymentStatus("Payout failed");
            sellerPaymentRepository.save(seller);


            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }
}