package com.matevskial.jh2patm.atm;

import com.matevskial.jh2patm.atm.Atm.State;
import com.matevskial.jh2patm.bank.BankService;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ViewBalanceView implements View {

  private final Atm atm;
  private final BankService bankService;
  private final Keypad keypad;


  @Override
  public void display() {
    System.out.println("Your account balance: " + bankService.getAccountBalance(atm.getUserSession().getAccountId()));
    State nextState = null;
    while(nextState == null) {
      System.out.printf("\t0 - Back to menu%n");
      String keypadInput = keypad.getInput();
      if("0".equals(keypadInput)) {
        nextState = State.MENU;
      } else {
        System.out.println("Invalid option selected " + keypadInput);
      }
    }
    atm.setState(nextState);
  }
}
