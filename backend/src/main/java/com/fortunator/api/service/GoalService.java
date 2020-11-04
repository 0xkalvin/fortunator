package com.fortunator.api.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.schemas.GoalSchema;
import com.fortunator.api.models.Goal;
import com.fortunator.api.models.GoalTypeEnum;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.GoalRepository;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@Service
public class GoalService {

    @Autowired
    private GoalRepository goalRepository;

    @Autowired
    private UserRepository userRepository;

    public Goal createGoal(GoalSchema goalPayload) {
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

    public Optional<Goal> findById(Long id) {
        return goalRepository.findById(id);
    }
}
