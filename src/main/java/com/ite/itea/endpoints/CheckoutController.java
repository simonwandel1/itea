package com.ite.itea.endpoints;

import com.ite.itea.application.dto.OrderRequest;
import com.ite.itea.application.dto.Receipt;
import com.ite.itea.application.dto.ReceiptResponse;
import com.ite.itea.application.usecase.PurchaseOrder;
import com.ite.itea.persistence.InMemoryProductRepository;
import com.ite.itea.presentation.ReceiptPresenter;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class CheckoutController {

    private final PurchaseOrder purchaseOrder = new PurchaseOrder(
            new InMemoryProductRepository(),
            new ReceiptPresenter()
    );

    @ResponseBody
    @PostMapping(path = "/checkout",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ReceiptResponse calculate(@RequestBody OrderRequest orderRequest) {
        final Receipt receipt = purchaseOrder.execute(orderRequest);
        return new ReceiptResponse(receipt.priceInCents(), receipt.text());
    }
}
