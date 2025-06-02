package com.EventsApi.model.coupon;

public record CouponRequestDTO(
    String code, 
    Integer discount, 
    Long valid) {
}
