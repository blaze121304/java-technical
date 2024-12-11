package com.rusty.api.service;

import com.rusty.api.domain.Coupon;
import com.rusty.api.repository.CouponCountRepository;
import com.rusty.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
    }

    public void apply(Long userId){
        //redis : incr
        //long count  = couponRepository.count();
        Long count = couponCountRepository.increment();

        if(count > 100){
            return;
        }

        couponRepository.save(new Coupon(userId));
    }
}
