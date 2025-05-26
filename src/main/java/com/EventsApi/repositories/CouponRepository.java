package com.EventsApi.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.EventsApi.domain.coupon.Coupon;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

}
