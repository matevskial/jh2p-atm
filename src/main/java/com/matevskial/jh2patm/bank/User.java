package com.matevskial.jh2patm.bank;

import java.math.BigDecimal;
import lombok.Getter;

class User {

  private UserAuthenticationInput userAuthenticationInput = UserAuthenticationInput.builder().accountNumber("12345").pin("54321").build();
  @Getter
  private final String accountId = "abc-efg-123";
  @Getter
  private BigDecimal balance = new BigDecimal("1000");

  public boolean authenticate(UserAuthenticationInput userAuthenticationInput) {
    return this.userAuthenticationInput.equals(userAuthenticationInput);
  }

  public void withdraw(BigDecimal amountToWithdraw) {
    balance = balance.subtract(amountToWithdraw);
  }

  void deposit(BigDecimal amountToDeposit) {
    balance = balance.add(amountToDeposit);
  }
}
