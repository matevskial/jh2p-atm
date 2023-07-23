package com.matevskial.jh2patm.bank;

import java.math.BigDecimal;

public interface BankService {

  UserAuthenticationResponse authenticateUser(UserAuthenticationInput userAuthenticationInput);

  BigDecimal getAccountBalance(String accountId);

  void withdraw(String accountId, BigDecimal amountToWithdraw);

  void deposit(String accountId, BigDecimal amountToDeposit);
}
