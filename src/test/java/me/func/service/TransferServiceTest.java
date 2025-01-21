package me.func.service;

import me.func.infrastructure.dao.Account;
import me.func.infrastructure.dao.User;
import me.func.domain.service.TransferService;
import me.func.domain.service.UserService;
import me.func.infrastructure.exception.transfer.InsufficientFundsException;
import me.func.infrastructure.exception.transfer.InvalidTransferAmountException;
import me.func.infrastructure.exception.transfer.SameAccountTransferException;
import me.func.infrastructure.exception.user.UserNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TransferServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private TransferService transferService;

    private static final Long FROM_USER_ID = 1L;
    private static final Long TO_USER_ID = 2L;
    private static final BigDecimal INITIAL_FROM_BALANCE = new BigDecimal("1000.00");
    private static final BigDecimal INITIAL_TO_BALANCE = new BigDecimal("500.00");

    private User fromUser;
    private User toUser;
    private Account fromAccount;
    private Account toAccount;

    @BeforeEach
    void setUp() {
        fromUser = createUser(FROM_USER_ID, INITIAL_FROM_BALANCE);
        toUser = createUser(TO_USER_ID, INITIAL_TO_BALANCE);
        fromAccount = fromUser.getAccount();
        toAccount = toUser.getAccount();
    }

    @Test
    void shouldTransferMoneyBetweenAccounts() {
        BigDecimal transferAmount = new BigDecimal("300.00");
        when(userService.getUser(FROM_USER_ID)).thenReturn(fromUser);
        when(userService.getUser(TO_USER_ID)).thenReturn(toUser);

        transferService.transfer(FROM_USER_ID, TO_USER_ID, transferAmount);

        assertThat(fromAccount.getBalance()).isEqualByComparingTo("700.00");
        assertThat(toAccount.getBalance()).isEqualByComparingTo("800.00");
        verify(userService).getUser(FROM_USER_ID);
        verify(userService).getUser(TO_USER_ID);
    }

    @Test
    void shouldThrowExceptionWhenTransferringToSameAccount() {
        BigDecimal transferAmount = new BigDecimal("100.00");

        assertThatThrownBy(() -> transferService.transfer(FROM_USER_ID, FROM_USER_ID, transferAmount))
                .isInstanceOf(SameAccountTransferException.class);

        verify(userService, never()).getUser(any());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsNegative() {
        BigDecimal transferAmount = new BigDecimal("-100.00");

        assertThatThrownBy(() -> transferService.transfer(FROM_USER_ID, TO_USER_ID, transferAmount))
                .isInstanceOf(InvalidTransferAmountException.class);

        verify(userService, never()).getUser(any());
    }

    @Test
    void shouldThrowExceptionWhenAmountIsZero() {
        BigDecimal transferAmount = BigDecimal.ZERO;

        assertThatThrownBy(() -> transferService.transfer(FROM_USER_ID, TO_USER_ID, transferAmount))
                .isInstanceOf(InvalidTransferAmountException.class);

        verify(userService, never()).getUser(any());
    }

    @Test
    void shouldThrowExceptionWhenInsufficientFunds() {
        BigDecimal transferAmount = new BigDecimal("1500.00");
        when(userService.getUser(FROM_USER_ID)).thenReturn(fromUser);
        when(userService.getUser(TO_USER_ID)).thenReturn(toUser);

        assertThatThrownBy(() -> transferService.transfer(FROM_USER_ID, TO_USER_ID, transferAmount))
                .isInstanceOf(InsufficientFundsException.class);

        assertThat(fromAccount.getBalance()).isEqualByComparingTo(INITIAL_FROM_BALANCE);
        assertThat(toAccount.getBalance()).isEqualByComparingTo(INITIAL_TO_BALANCE);
    }

    @Test
    void shouldThrowExceptionWhenUserNotFound() {
        BigDecimal transferAmount = new BigDecimal("100.00");
        when(userService.getUser(FROM_USER_ID)).thenThrow(UserNotFoundException.class);

        assertThatThrownBy(() -> transferService.transfer(FROM_USER_ID, TO_USER_ID, transferAmount))
                .isInstanceOf(UserNotFoundException.class);

        assertThat(fromAccount.getBalance()).isEqualByComparingTo(INITIAL_FROM_BALANCE);
        assertThat(toAccount.getBalance()).isEqualByComparingTo(INITIAL_TO_BALANCE);
    }

    private User createUser(Long id, BigDecimal balance) {
        User user = new User();
        user.setId(id);
        Account account = new Account();
        account.setBalance(balance);
        user.setAccount(account);
        return user;
    }
} 