package com.matevskial.jh2patm;

import com.matevskial.jh2patm.atm.Atm;
import com.matevskial.jh2patm.atm.DepositSlot;
import com.matevskial.jh2patm.atm.FakeDepositSlot;
import com.matevskial.jh2patm.atm.FakeWithdrawSlot;
import com.matevskial.jh2patm.atm.Keypad;
import com.matevskial.jh2patm.atm.StandardIoKeypad;
import com.matevskial.jh2patm.atm.WithdrawSlot;
import com.matevskial.jh2patm.bank.BankService;
import com.matevskial.jh2patm.bank.BankServiceImpl;

public class Main {

  public static void main(String[] args) {
    System.out.println("Java how to program, ATM");
    BankService bankService = new BankServiceImpl();
    Keypad keypad = new StandardIoKeypad();
    DepositSlot depositSlot = new FakeDepositSlot();
    WithdrawSlot withdrawSlot = new FakeWithdrawSlot();
    Atm atm = new Atm(bankService, keypad, depositSlot, withdrawSlot);
    atm.run();
  }
}
