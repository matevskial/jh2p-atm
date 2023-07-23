package com.matevskial.jh2patm.atm;

import java.util.Scanner;

public class FakeWithdrawSlot implements WithdrawSlot {

  @Override
  public void completeWithdraw() {
    System.out.println("(Simulate withdraw slot) Press enter to complete withdraw");
    new Scanner(System.in).nextLine();
  }
}
