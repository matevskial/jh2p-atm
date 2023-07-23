package com.matevskial.jh2patm.atm;

public class DepositSlotInactivityException extends RuntimeException {

  public DepositSlotInactivityException() {
    super("Deposit slot inactivity");
  }
}
