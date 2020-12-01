package com.fortunator.api.config;

import java.math.BigDecimal;
import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import com.fortunator.api.models.Balance;
import com.fortunator.api.models.Goal;
import com.fortunator.api.models.GoalStatusEnum;
import com.fortunator.api.models.GoalTypeEnum;
import com.fortunator.api.models.Level;
import com.fortunator.api.models.LevelNameEnum;
import com.fortunator.api.models.Transaction;
import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.models.TransactionTypeEnum;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.BalanceRepository;
import com.fortunator.api.repository.GoalRepository;
import com.fortunator.api.repository.LevelRepository;
import com.fortunator.api.repository.TransactionCategoryRepository;
import com.fortunator.api.repository.TransactionRepository;
import com.fortunator.api.repository.UserRepository;

@Configuration
@Profile("dev")
public class DevConfig implements CommandLineRunner {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TransactionCategoryRepository transactionCategoryRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private BalanceRepository balanceRepository;
	
	@Autowired
	private LevelRepository levelRepository;

	@Autowired
	private GoalRepository goalRepository;

	@Override
	public void run(String... args) throws Exception {

		User user = new User();
		user.setName("Zé");
		user.setEmail("wendel_duarte@outlook.com");
		user.setPassword(String.valueOf("senha".hashCode()));
		
		user.setScore(BigDecimal.valueOf(0));

		Balance balance = new Balance();
		balance.setUser(user);
		balance.setAmount(BigDecimal.valueOf(20.0));
		
		Level level = new Level();
		level.setUser(user);
		level.setLevel(1);
		level.setLevelName(LevelNameEnum.INICIANTE.getDescription());
		level.setLevelScore(BigDecimal.valueOf(0));
		level.setMaxLevelScore();
		
		user.setBalance(balance);
		user.setLevel(level);

		userRepository.save(user);
		levelRepository.save(level);
		balanceRepository.save(balance);

		TransactionCategory transactionCategorySalary = new TransactionCategory();
		transactionCategorySalary.setUser(user);
		transactionCategorySalary.setName("salary");
		transactionCategorySalary.setDescription("salary");
		transactionCategorySalary.setActive(true);

		TransactionCategory transactionCategoryFastFood = new TransactionCategory();
		transactionCategoryFastFood.setUser(user);
		transactionCategoryFastFood.setName("fast_food");
		transactionCategoryFastFood.setDescription("Fast food");
		transactionCategoryFastFood.setActive(true);
		
		transactionCategoryRepository.save(transactionCategorySalary);
		transactionCategoryRepository.save(transactionCategoryFastFood);

		Transaction transactionSalary = new Transaction();
		transactionSalary.setType(TransactionTypeEnum.INCOMING);
		transactionSalary.setTransactionCategory(transactionCategorySalary);
		transactionSalary.setUser(user);
		transactionSalary.setAmount(new BigDecimal(2000.0));
		transactionSalary.setDescription("Salary Incoming");
		transactionSalary.setDate(LocalDate.now());

		Transaction transactionFastFood = new Transaction();
		transactionFastFood.setType(TransactionTypeEnum.EXPENSE);
		transactionFastFood.setTransactionCategory(transactionCategoryFastFood);
		transactionFastFood.setUser(user);
		transactionFastFood.setAmount(new BigDecimal(50.0));
		transactionFastFood.setDescription("Dinner");
		transactionFastFood.setDate(LocalDate.now());

		transactionRepository.save(transactionSalary);
		transactionRepository.save(transactionFastFood);

		Goal goal = new Goal();
		goal.setAmount(new BigDecimal(50000.0));
		goal.setDate(LocalDate.of(2020, 12, 2));
		goal.setDescription("Comprar um carro");
		goal.setType(GoalTypeEnum.BUDGET);
		goal.setUser(user);
		goal.setScore(goal.calculateScore());
		goal.setStatus(GoalStatusEnum.IN_PROGRESS);
		goal.setProgressAmount(BigDecimal.valueOf(0));
		goal.setProgressPercentage(BigDecimal.valueOf(0));
		
		Goal goal2 = new Goal();
		goal2.setAmount(new BigDecimal(300.0));
		goal2.setDate(LocalDate.of(2020, 12, 2));
		goal2.setDescription("Comprar um relogio");
		goal2.setType(GoalTypeEnum.BUDGET);
		goal2.setUser(user);
		goal2.setScore(goal2.calculateScore());
		goal2.setStatus(GoalStatusEnum.IN_PROGRESS);
		goal2.setProgressAmount(BigDecimal.valueOf(0));
		goal2.setProgressPercentage(BigDecimal.valueOf(0));

		goalRepository.save(goal);
		goalRepository.save(goal2);
	}
}
