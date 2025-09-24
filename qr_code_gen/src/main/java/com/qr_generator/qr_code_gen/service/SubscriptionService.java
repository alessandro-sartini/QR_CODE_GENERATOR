package com.qr_generator.qr_code_gen.service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import javax.management.RuntimeErrorException;

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

    public SubscriptionResponseDto createOrUpdateSubscription(SubscriptionRequestDto dto) {
        // Crea o aggiorna la subscription di un utente
        User user = userRepo.findById(dto.getUserId())
                .orElseThrow(() -> new RuntimeException("user not found"));

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
            // endDate logica tua business
            subscription.setCreatedAt(Instant.now());
        }

        Subscription saved = subscriptionRepo.save(subscription);
        return subscriptionMapper.toDto(saved);
    }

    public SubscriptionResponseDto getSubscriptionByUserId(Long userId) {
        Optional<User> user = userRepo.findById(userId);

        if (user.isEmpty()) {
            throw new RuntimeException("user not found");
        }
        Optional<Subscription> optional = subscriptionRepo.findByUser(user.get());

        return subscriptionMapper.toDto(optional.get());
        
    }

    public List<SubscriptionResponseDto> getAllSubscriptions() {
        
        List<Subscription> subscriptions = subscriptionRepo.findAll();

        if (subscriptions.isEmpty()) {
            throw new RuntimeException("subscriptions not found");
        } 
        return subscriptions.stream()
        .map((sub) -> subscriptionMapper.toDto(sub))
        .toList();

        
    }

}
