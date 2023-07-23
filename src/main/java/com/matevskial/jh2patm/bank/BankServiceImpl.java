package com.matevskial.jh2patm.bank;

import java.math.BigDecimal;

public class BankServiceImpl implements BankService {

  private final User user = new User();

  @Override
  public UserAuthenticationResponse authenticateUser(UserAuthenticationInput userAuthenticationInput) {
    if (user.authenticate(userAuthenticationInput)) {
      return UserAuthenticationResponse.builder().accountId("abc-efg-123").build();
    }
    return UserAuthenticationResponse.builder().build();
  }

  @Override
  public BigDecimal getAccountBalance(String accountId) {
    return user.getBalance();
  }

  @Override
  public void withdraw(String accountId, BigDecimal amountToWithdraw) {
    if(amountToWithdraw.compareTo(user.getBalance()) > 0) {
      throw new BankAccountInsuficientBalance();
    }
    user.withdraw(amountToWithdraw);
  }

  @Override
  public void deposit(String accountId, BigDecimal amountToDeposit) {
    if(amountToDeposit.compareTo(BigDecimal.ZERO) < 0) {
      throw new BankDepositException();
    }
    user.deposit(amountToDeposit);
  }
}
