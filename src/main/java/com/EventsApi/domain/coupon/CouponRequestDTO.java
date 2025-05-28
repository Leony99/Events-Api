package com.EventsApi.domain.coupon;

public record CouponRequestDTO(
    String code, 
    Integer discount, 
    Long valid) {
}
