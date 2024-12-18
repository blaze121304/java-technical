package com.rusty.api.service;

import com.rusty.api.domain.Coupon;
import com.rusty.api.producer.CouponCreateProducer;
import com.rusty.api.repository.CouponCountRepository;
import com.rusty.api.repository.CouponRepository;
import org.springframework.stereotype.Service;

@Service
public class ApplyService {

    private final CouponRepository couponRepository;

    private final CouponCountRepository couponCountRepository;
    private final CouponCreateProducer couponCreateProducer;

    public ApplyService(CouponRepository couponRepository, CouponCountRepository couponCountRepository, CouponCreateProducer couponCreateProducer) {
        this.couponRepository = couponRepository;
        this.couponCountRepository = couponCountRepository;
        this.couponCreateProducer = couponCreateProducer;
    }

    public void apply(Long userId){
        //long count  = couponRepository.count();           //한번 응모시
        Long count = couponCountRepository.increment();     //백번 응모시

        if(count > 100){
            return;
        }

        couponCreateProducer.create(userId);
        //couponRepository.save(new Coupon(userId));
    }
}
