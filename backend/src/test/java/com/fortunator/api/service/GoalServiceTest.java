package com.fortunator.api.service;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.fortunator.api.models.Goal;
import com.fortunator.api.models.GoalStatusEnum;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.GoalRepository;
import com.fortunator.api.repository.LevelRepository;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.schemas.UpdateGoalSchema;
import com.fortunator.api.service.exceptions.ResourceNotFoundException;

@ContextConfiguration(classes = { GoalService.class })
@ExtendWith(MockitoExtension.class)
public class GoalServiceTest {
	
	private static final BigDecimal TOTAL_AMOUNT = BigDecimal.valueOf(2000);
	private static final BigDecimal TOTAL_PROGRESS_AMOUNT = BigDecimal.valueOf(2000);
	private static final BigDecimal GOAL_SCORE= BigDecimal.valueOf(200);
	private static final BigDecimal PROGRESS_AMOUNT = BigDecimal.valueOf(100);
	
	@Mock
	private GoalRepository goalRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@Mock
	private LevelRepository levelRepository;
	
	@Mock
	private UpdateGoalSchema updateGoalSchema;
	
	@Mock
	private Goal goal;
	
	@Mock
	private User user;
	
	@Mock
	private CompletableFuture<Optional<Goal>> completableFutureGoal;
	
	@InjectMocks
	private GoalService goalService;
	
	
	@Test
	public void shouldUpdateProgressAndStatus() throws ResourceNotFoundException, InterruptedException, ExecutionException {
		when(goalRepository.getById(anyLong())).thenReturn(completableFutureGoal);
		when(completableFutureGoal.get()).thenReturn(Optional.of(goal));
		when(goal.getUser()).thenReturn(user);
		when(goal.getProgressAmount()).thenReturn(TOTAL_PROGRESS_AMOUNT);
		when(updateGoalSchema.getProgressAmount()).thenReturn(TOTAL_PROGRESS_AMOUNT);
		when(goal.getAmount()).thenReturn(TOTAL_AMOUNT);
		when(goal.getScore()).thenReturn(GOAL_SCORE);
		
		goalService.updateGoal(updateGoalSchema);
		
		verify(goal).setStatus(eq(GoalStatusEnum.DONE));
		verify(user).addToScore(eq(GOAL_SCORE));
		
	}
	
	@Test
	public void shouldUpdateJustProgress() throws ResourceNotFoundException, InterruptedException, ExecutionException {
		when(goalRepository.getById(anyLong())).thenReturn(completableFutureGoal);
		when(completableFutureGoal.get()).thenReturn(Optional.of(goal));
		when(goal.getUser()).thenReturn(user);
		when(goal.getProgressAmount()).thenReturn(PROGRESS_AMOUNT);
		when(updateGoalSchema.getProgressAmount()).thenReturn(PROGRESS_AMOUNT);
		when(goal.getAmount()).thenReturn(TOTAL_AMOUNT);
		
		goalService.updateGoal(updateGoalSchema);
		
		verify(goal, never()).setStatus(eq(GoalStatusEnum.DONE));
		verify(user, never()).addToScore(eq(GOAL_SCORE));
		
	}

}