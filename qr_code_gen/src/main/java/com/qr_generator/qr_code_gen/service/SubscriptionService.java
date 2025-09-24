package com.qr_generator.qr_code_gen.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.qr_generator.qr_code_gen.dto.subscription.SubscriptionRequestDto;
import com.qr_generator.qr_code_gen.dto.subscription.SubscriptionResponseDto;
import com.qr_generator.qr_code_gen.entity.Status;
import com.qr_generator.qr_code_gen.entity.Subscription;
import com.qr_generator.qr_code_gen.entity.SubscriptionPlan;
import com.qr_generator.qr_code_gen.entity.User;
import com.qr_generator.qr_code_gen.mapper.subscription.SubscriptionMapper;
import com.qr_generator.qr_code_gen.repository.SubscriptionRepo;
import com.qr_generator.qr_code_gen.repository.UserRepo;
import com.qr_generator.qr_code_gen.exceptions.UserNotFoundException;
import com.qr_generator.qr_code_gen.exceptions.SubscriptionNotFoundException;

@Service
public class SubscriptionService {
    private final SubscriptionRepo subscriptionRepo;
    private final SubscriptionMapper subscriptionMapper;
    private final UserRepo userRepo;

    public SubscriptionService(SubscriptionMapper subscriptionMapper, SubscriptionRepo subscriptionRepo,
            UserRepo userRepo) {
        this.subscriptionMapper = subscriptionMapper;
        this.subscriptionRepo = subscriptionRepo;
        this.userRepo = userRepo;
    }

    /**
     * Creates or updates a user's subscription.
     */
    public SubscriptionResponseDto createOrUpdateSubscription(SubscriptionRequestDto dto) {
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Optional<Subscription> optional = subscriptionRepo.findByUserId(dto.getUserId());
        Subscription subscription;
        if (optional.isPresent()) {
            subscription = optional.get();
            subscription.setSubscriptionPlan(SubscriptionPlan.valueOf(dto.getPlanType()));
            subscription.setStatus(Status.ACTIVE);
            subscription.setStartDate(LocalDate.now());
        } else {
            subscription = new Subscription();
            subscription.setUser(user);
            subscription.setSubscriptionPlan(SubscriptionPlan.valueOf(dto.getPlanType()));
            subscription.setStatus(Status.ACTIVE);
            subscription.setStartDate(LocalDate.now());
            subscription.setCreatedAt(Instant.now());
        }
        Subscription saved = subscriptionRepo.save(subscription);
        return subscriptionMapper.toDto(saved);
    }

    /**
     * Gets a subscription by user ID.
     */
    public SubscriptionResponseDto getSubscriptionByUserId(Long userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));
        Optional<Subscription> optional = subscriptionRepo.findByUser(user);
        return optional.map(subscriptionMapper::toDto)
                .orElseThrow(() -> new SubscriptionNotFoundException("Subscription not found"));
    }

    /**
     * Gets all subscriptions in the system.
     */
    public List<SubscriptionResponseDto> getAllSubscriptions() {
        List<Subscription> subscriptions = subscriptionRepo.findAll();
        if (subscriptions.isEmpty()) {
            throw new SubscriptionNotFoundException("No subscriptions found");
        }
        return subscriptions.stream()
                .map(subscriptionMapper::toDto)
                .toList();
    }
}
