package com.ite.itea.application.usecase;

import com.ite.itea.application.dto.OrderRequest;
import com.ite.itea.application.dto.Receipt;
import com.ite.itea.domain.retail.Order;
import com.ite.itea.domain.retail.ProductId;
import com.ite.itea.domain.retail.ProductRepository;
import com.ite.itea.presentation.ReceiptPresenter;

import java.util.ArrayList;
import java.util.List;

public class PurchaseOrder {

    private final ProductRepository productRepository;
    private final ReceiptPresenter receiptPresenter;

    public PurchaseOrder(ProductRepository productRepository, ReceiptPresenter receiptPresenter) {
        this.productRepository = productRepository;
        this.receiptPresenter = receiptPresenter;
    }

    public Receipt execute(OrderRequest orderRequest) {
        final List<Order.OrderItem> orderItems = new ArrayList<>();

        for (var itemRequest : orderRequest.itemRequests()) {
            var productId = new ProductId(itemRequest.name().name());
            var product = productRepository.byId(productId);
            product.ifPresent(value ->
                    orderItems.add(new Order.OrderItem(value, itemRequest.amount())));
        }

        return receiptPresenter.prepareReceipt(new Order(orderItems));
    }
}
