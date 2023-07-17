package com.ite.itea.presentation;

import com.ite.itea.application.dto.Receipt;
import com.ite.itea.domain.core.EuroPrice;
import com.ite.itea.domain.retail.Order;

import java.text.MessageFormat;
import java.util.stream.Collectors;

public class ReceiptPresenter {

    public Receipt prepareReceipt(Order order) {
        final var price = totalPrice(order);
        final var text = getText(order);

        return new Receipt(price.asCents(), text);
    }

    private String getText(Order order) {
        final var formattedProducts = order.items().stream()
                .map(this::formatOrderItem)
                .collect(Collectors.joining());

        return "itea \n"
                + formattedProducts
                + "Total " + totalPrice(order).formatPrice();
    }

    private String formatOrderItem(Order.OrderItem orderItem) {
        if (orderItem.amount() == 0) {
            return "";
        }

        final var product = orderItem.product();
        final var productName = product.name();
        final var price = product.price().formatPrice();
        final var amount = orderItem.amount();
        return MessageFormat.format("{0} {1} * {2}\n", productName, price, amount);
    }

    private EuroPrice totalPrice(Order order) {
        return order.items().stream()
                .map(orderItem -> orderItem.product().price().times(orderItem.amount()))
                .reduce(EuroPrice.zero(), EuroPrice::plus);
    }
}
