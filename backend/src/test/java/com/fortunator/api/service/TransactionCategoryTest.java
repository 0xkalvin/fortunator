package com.fortunator.api.service;

import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ContextConfiguration;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import com.fortunator.api.models.User;
import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.repository.TransactionCategoryRepository;

import com.fortunator.api.service.exceptions.*;

@ContextConfiguration(classes = { TransactionCategoryService.class })
@ExtendWith(MockitoExtension.class)
public class TransactionCategoryTest {
    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionCategoryRepository transactionCategoryRepository;

    @InjectMocks
    private TransactionCategoryService transactionCategoryService;

    @Test
    public void shouldCreateTransactionCategorySuccessfully() throws Exception {
        long userId = 10;
        
        final User user = new User(userId, "roger", "roger@email.com", "senhadoroger");
        final TransactionCategory category = new TransactionCategory(null, "delivery",
                "Aquelas comidinhas que peço de noite né", user);

        doReturn(Optional.of(user)).when(userRepository).findById(userId);
        doReturn(category).when(transactionCategoryRepository).save(category);

        TransactionCategory savedCategory = transactionCategoryService.createCategory(category);

        assertTrue(savedCategory.getId() == category.getId());
    }

    @Test
    public void shouldNotCreateTransactionCategoryIfUserDoesNotExist() throws Exception {
        long userId = 10;
        
        final User user = new User(userId, "roger", "roger@email.com", "senhadoroger");
        final TransactionCategory category = new TransactionCategory(null, "delivery",
                "Aquelas comidinhas que peço de noite né", user);

        doReturn(Optional.empty()).when(userRepository).findById(userId);

        UserNotFoundException thrown = assertThrows(UserNotFoundException.class, () -> transactionCategoryService.createCategory(category));
        
		assertTrue(thrown.getMessage().contains("User does not exist"));
    }

    @Test
    public void shouldReturnCategoriesByUser() throws Exception {
        Long userId = 10L;
        
        final User user = new User(userId, "roger", "roger@email.com", "senhadoroger");
        
        final TransactionCategory category = new TransactionCategory(10L, "delivery",
                "Aquelas comidinhas que peço de noite né", user);


        doReturn(new ArrayList<TransactionCategory>(List.of(category))).when(transactionCategoryRepository).findByUserId(userId);
        doReturn(new ArrayList<TransactionCategory>(List.of(category))).when(transactionCategoryRepository).findByIsDefault(true);

        List<TransactionCategory> categories = transactionCategoryService.getCategoriesByUserId(userId);

        assertTrue(categories.contains(category));
    }

}
