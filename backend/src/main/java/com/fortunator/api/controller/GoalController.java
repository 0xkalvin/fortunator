package com.fortunator.api.controller;

import java.util.List;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fortunator.api.schemas.GoalSchema;
import com.fortunator.api.models.Goal;

import com.fortunator.api.service.GoalService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@CrossOrigin
@RestController
@RequestMapping(value = "/goals")
public class GoalController {

	private static final int SC_OK = 200;
	private static final int SC_BAD_REQUEST = 400;
	private static final int NO_CONTENT = 204;

	@Autowired
	private GoalService goalService;

	@ApiOperation(value = "Create a new goal ")
	@ApiResponses(value = {
			@ApiResponse(code = SC_BAD_REQUEST, message = "One or more fields were filled in incorrectly") })
	@CrossOrigin
	@PostMapping
	public ResponseEntity<Goal> createGoal(@Valid @RequestBody GoalSchema goal) {
		Goal createdGoal = goalService.createGoal(goal);

		return new ResponseEntity<Goal>(createdGoal, HttpStatus.CREATED);
	}

	@CrossOrigin
	@ApiOperation(value = "Get all goals by user id")
	@ApiResponses(value = { @ApiResponse(code = SC_OK, message = "ok"),
			@ApiResponse(code = NO_CONTENT, message = "When has no goals.") })
	@GetMapping
	public ResponseEntity<List<Goal>> getGoalsByUserId(@RequestParam("user_id") Long userId) {
		List<Goal> goals = goalService.getGoalsByUserId(userId);

		if (goals.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}

		return new ResponseEntity<List<Goal>>(goals, HttpStatus.OK);
	}
}
