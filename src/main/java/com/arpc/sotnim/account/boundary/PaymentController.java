package com.arpc.sotnim.account.boundary;

import com.arpc.sotnim.account.boundary.dto.PaymentRequest;
import com.arpc.sotnim.account.boundary.dto.PaymentResponse;
import com.arpc.sotnim.account.control.PaymentService;
import com.arpc.sotnim.core.dto.RestResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/api/v1/payments")
    public RestResponse<PaymentResponse> transfer(@RequestBody PaymentRequest transferRequest) {
        var payment = paymentService.transfer(transferRequest);
        return RestResponse.success(new PaymentResponse(payment.getPaymentId(), payment.getCreatedAt()));
    }
}
