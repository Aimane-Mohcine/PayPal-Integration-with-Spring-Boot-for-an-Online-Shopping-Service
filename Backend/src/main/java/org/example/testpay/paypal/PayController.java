package org.example.testpay.paypal;

import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.example.testpay.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Map;

@RestController
@RequestMapping("/api/pay")
@RequiredArgsConstructor
@CrossOrigin
public class PayController {

    private final PaypalService paypalService;

    private org.example.testpay.Entities.Payment obj ;
    @Autowired
    PaymentRepository paymentRepository;
    @PostMapping("/create")
    public Map<String, Object> createPayment(@RequestBody Map<String, String> request) {
        try {
            obj=new org.example.testpay.Entities.Payment();
            Double amount = Double.parseDouble(request.get("amount"));
            String currency = request.get("currency");
            String description = request.get("description");
            String idOrder = request.get("idOrder");  // Récupérer l'idOrder de la requête

            System.out.println("idOrder received: " + idOrder);  // Ajoutez un log pour vérifier la valeur de idOrder

            if (idOrder == null || idOrder.isEmpty()) {
                throw new IllegalArgumentException("idOrder is required and cannot be null or empty");
            }

            // Création du paiement PayPal
            Payment payment = paypalService.createPayment(
                    amount,
                    currency,
                    "paypal",
                    "sale",
                    description,
                    "http://localhost:8081/api/pay/cancel?idOrder=" + idOrder,  // Ajouter idOrder à l'URL d'annulation
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




    @GetMapping("/success")
    public RedirectView successPay(@RequestParam("paymentId") String paymentId, @RequestParam("PayerID") String payerId, @RequestParam("idOrder") String idOrder) {
        try {
            Payment payment = paypalService.executePayment(paymentId, payerId);
            if ("approved".equals(payment.getState())) {
                // Redirige vers l'URL de succès avec l'idOrder
                obj.setStatus("successful");
                paymentRepository.save(obj);
                return new RedirectView("http://localhost:4200/suc");
            }
        } catch (PayPalRESTException e) {
            e.printStackTrace();
        }
        // En cas d'échec, redirige vers l'URL d'annulation avec l'idOrder
        obj.setStatus("failed");
        paymentRepository.save(obj);
        return new RedirectView("http://localhost:4200/can");
    }

    @GetMapping("/cancel")
    public RedirectView cancelPay() {
        obj.setStatus("failed");
        paymentRepository.save(obj);
        return new RedirectView("http://localhost:4200/can");
    }
}