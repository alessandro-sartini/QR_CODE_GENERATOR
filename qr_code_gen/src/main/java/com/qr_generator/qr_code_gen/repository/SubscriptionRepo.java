package com.qr_generator.qr_code_gen.repository;

import java.io.ObjectInputFilter.Status;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.qr_generator.qr_code_gen.entity.Subscription;
import com.qr_generator.qr_code_gen.entity.SubscriptionPlan;
import com.qr_generator.qr_code_gen.entity.User;

@Repository
public interface SubscriptionRepo extends JpaRepository<Subscription, Long> {

    Optional<Subscription> findByUser(User user);

    Optional<Subscription> findByUserId(Long userId);

    List<Subscription> findByStatus(Status status);

    List<Subscription> findBySubscriptionPlan(SubscriptionPlan subscriptionPlan);

}
