package com.matevskial.jh2patm.bank;

public class BankAccountInsuficientBalance extends RuntimeException {

  public BankAccountInsuficientBalance() {
    super("Insuficient balance");
  }
}
