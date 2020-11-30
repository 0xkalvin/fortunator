package com.fortunator.api.service;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;

import com.fortunator.api.models.TransactionCategory;
import com.fortunator.api.models.User;
import com.fortunator.api.repository.TransactionCategoryRepository;
import com.fortunator.api.repository.UserRepository;
import com.fortunator.api.service.exceptions.UserNotFoundException;

@ContextConfiguration(classes = { TransactionCategoryService.class })
@ExtendWith(MockitoExtension.class)
public class TransactionCategoryServiceTest {
	
	private static final Long USER_ID = 1L;
	private static final Long CATEGORY_ID = 1L;
	private static final int LIST_SIZE = 1;
	private static final boolean ACTIVE = true;
	private static final boolean DISABLED = false;
	
    @Mock
    private UserRepository userRepository;

    @Mock
    private TransactionCategoryRepository transactionCategoryRepository;
    
    @Mock
    private TransactionCategory category;

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
    
    @Test
    public void shouldReturnListTransactions() {
    	when(transactionCategoryRepository.findByUserId(eq(USER_ID))).thenReturn(asList(category));
    	when(category.isActive()).thenReturn(ACTIVE);
    	
    	List<TransactionCategory> methodResult = transactionCategoryService.getCreatedCategories(USER_ID);
    	
    	assertTrue(methodResult.size() == LIST_SIZE);
    }
    
    @Test
    public void givenIsDisabledCategoryThenShouldReturnEmptyList() {
    	when(transactionCategoryRepository.findByUserId(eq(USER_ID))).thenReturn(asList(category));
    	when(category.isActive()).thenReturn(DISABLED);
    	
    	List<TransactionCategory> methodResult = transactionCategoryService.getCreatedCategories(USER_ID);
    	
    	assertTrue(methodResult.isEmpty());
    }
    
    @Test
    public void shouldDisableCategory() {
    	when(transactionCategoryRepository.findById(CATEGORY_ID)).thenReturn(Optional.of(category));
    	
    	transactionCategoryService.disableCategory(CATEGORY_ID);
    
    	verify(category).setActive(eq(DISABLED));
    }

}
