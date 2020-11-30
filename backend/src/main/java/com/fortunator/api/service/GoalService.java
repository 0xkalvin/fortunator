package com.fortunator.api.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fortunator.api.models.Goal;
import com.fortunator.api.models.GoalStatusEnum;
import com.fortunator.api.models.GoalTypeEnum;
import com.fortunator.api.models.Level;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.GoalRepository;
import com.fortunator.api.repository.LevelRepository;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.schemas.CreateGoalSchema;
import com.fortunator.api.schemas.UpdateGoalSchema;
import com.fortunator.api.service.exceptions.ResourceNotFoundException;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@Service
public class GoalService {

	@Autowired
	private GoalRepository goalRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private LevelRepository levelRepository;

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

	public Goal updateGoal(UpdateGoalSchema payload)
			throws ResourceNotFoundException, InterruptedException, ExecutionException {
		Goal goal = goalRepository.getById(payload.getGoalId()).get()
				.orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
		User user = goal.getUser();
		Level level = user.getLevel();

		goal.setProgressAmount(goal.getProgressAmount().add(payload.getProgressAmount()));

		if (!(goal.getAmount().compareTo(goal.getProgressAmount()) > 0)) {
			goal.setStatus(GoalStatusEnum.DONE);
			level = user.addToScore(goal.getScore());
		}

		goal.setProgressPercentage(calculateProgressPercentage(goal.getAmount(), goal.getProgressAmount()));

		goalRepository.save(goal);
		userRepository.save(user);

		return goal;
	}

	private BigDecimal calculateProgressPercentage(BigDecimal totalAmount, BigDecimal progressAmount) {
		return progressAmount.multiply(BigDecimal.valueOf(100)).divide(totalAmount, 2, RoundingMode.HALF_UP);
	}

	public void deleteGoal(Long id) {
    	goalRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Goal not found"));
    	goalRepository.deleteById(id);
    }
}
