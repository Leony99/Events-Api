package com.EventsApi.controller;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.EventsApi.model.coupon.Coupon;
import com.EventsApi.model.coupon.CouponRequestDTO;
import com.EventsApi.service.CouponService;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {
    @Autowired
    private CouponService couponService;

    @PostMapping("/event/{eventId}")
    public ResponseEntity<Coupon> addCouponToEvent(@PathVariable UUID eventId, CouponRequestDTO data) {
        Coupon coupon = couponService.addCouponToEvent(eventId, data);
        return ResponseEntity.ok(coupon);
    }
}
