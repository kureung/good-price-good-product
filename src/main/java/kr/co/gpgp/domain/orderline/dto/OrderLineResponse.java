package kr.co.gpgp.domain.orderline.dto;

import lombok.Data;

@Data
public class OrderLineResponse {

    private final String itemName;
    private final String itemCode;
    private final int itemPrice;
    private final int itemQuantity;

}
