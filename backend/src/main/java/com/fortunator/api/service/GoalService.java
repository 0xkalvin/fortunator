package com.fortunator.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.schemas.CreateGoalSchema;
import com.fortunator.api.schemas.UpdateGoalSchema;
import com.fortunator.api.models.Goal;
import com.fortunator.api.models.GoalTypeEnum;
import com.fortunator.api.models.GoalStatusEnum;

import com.fortunator.api.models.User;
import com.fortunator.api.repository.GoalRepository;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.ResourceNotFoundException;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    public Goal createGoal(CreateGoalSchema goalPayload) {
        BigDecimal parsedAmount = new BigDecimal(goalPayload.getAmout());
        LocalDate date = goalPayload.getDate();
        String description = goalPayload.getDescription();
        GoalTypeEnum type = goalPayload.getType();

        Goal goal = new Goal(parsedAmount, date, description, type);

        Long userId = goalPayload.getUser().getId();

        User user = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException("User does not exist"));

        goal.setUser(user);

        Goal createdGoal = goalRepository.save(goal);

        return createdGoal;
    }

    public List<Goal> getGoalsByUserId(Long userId) {
        List<Goal> goals = goalRepository.findByUserId(userId);

        return goals;
    }

    public Goal updateGoal(Long id, UpdateGoalSchema payload) {
        Long userId = payload.getUser().getId();

        CompletableFuture<Optional<Goal>> goalPromise = goalRepository.getById(id);
        CompletableFuture<Optional<User>> userPromise = userRepository.getById(userId);

        Goal goal;
        User user;

        try {
            goal = goalPromise.get().orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
            user = userPromise.get().orElseThrow(() -> new ResourceNotFoundException("User not found"));

        } catch (Exception e) {
            throw new ResourceNotFoundException(e.getMessage());
        }

        GoalStatusEnum newStatus = payload.getStatus();
        GoalStatusEnum currentStatus = goal.getStatus();
        Boolean isAlreadyOnFinalStatus = currentStatus == GoalStatusEnum.DONE || currentStatus == GoalStatusEnum.UNDONE;

        if (newStatus == currentStatus || isAlreadyOnFinalStatus) {
            return goal;
        }

        if (newStatus == GoalStatusEnum.DONE) {
            user.addToScore(goal.getScore());

            userRepository.save(user);
        }

        goal.setStatus(newStatus);

        Goal updatedGoal = goalRepository.save(goal);

        return updatedGoal;
    }
}
