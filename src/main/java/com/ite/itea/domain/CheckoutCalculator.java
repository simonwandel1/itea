package com.ite.itea.domain;

import com.ite.itea.application.dto.ProductDTO;
import com.ite.itea.domain.retail.Order;
import com.ite.itea.application.dto.ReceiptDTO;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.stream.Collectors;

public class CheckoutCalculator {

    public ReceiptDTO prepareReceipt(Order order) {
        var price = totalPrice(order);
        var text = getText(order, price);

        return new ReceiptDTO(price, text);
    }

    private String getText(Order order, long priceInCents) {
        final var formattedProducts = order.productDTOs().stream()
                .map(this::formatOrderItem)
                .collect(Collectors.joining());

        return "itea \n"
                + formattedProducts
                + "Total " + formatPrice(priceInCents);
    }

    private String formatOrderItem(ProductDTO productDTO) {
        if (productDTO.getAmount() <= 0) {
            return "";
        }

        final var productName = productDTO.getName();
        final var price = formatPrice(productDTO.getPriceInCents());
        final var amount = productDTO.getAmount();
        return MessageFormat.format("{0} {1} * {2}\n", productName, price, amount);
    }

    private String formatPrice(long priceInCents) {
        var currencyFormat = NumberFormat.getCurrencyInstance(Locale.GERMANY);
        var decimalPrice = BigDecimal.valueOf(priceInCents).movePointLeft(2);
        return currencyFormat.format(decimalPrice);
    }

    private long totalPrice(Order order) {
        return order.productDTOs().stream()
                .mapToLong(product -> product.getAmount() * product.getPriceInCents())
                .sum();
    }
}
