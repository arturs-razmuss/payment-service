package com.arpc.sotnim.account.boundary;

import com.arpc.sotnim.account.boundary.dto.TransferRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class PaymentController {

    @PostMapping("/api/v1/payments")
    public void transfer(@RequestBody TransferRequest transferRequest) {

    }
}
