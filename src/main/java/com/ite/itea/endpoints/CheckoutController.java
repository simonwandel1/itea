package com.ite.itea.endpoints;

import com.ite.itea.application.dto.OrderRequest;
import com.ite.itea.application.dto.ReceiptDTO;
import com.ite.itea.application.dto.ReceiptResponse;
import com.ite.itea.application.usecase.OrderProductsUseCase;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckoutController {

    private final OrderProductsUseCase orderProductsUseCase = new OrderProductsUseCase();

    @ResponseBody
    @PostMapping(path = "/checkout",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ReceiptResponse calculate(@RequestBody OrderRequest orderRequest) {
        final ReceiptDTO receiptDto = orderProductsUseCase.execute(orderRequest);
        return new ReceiptResponse(receiptDto.priceInCents(), receiptDto.text());
    }
}
