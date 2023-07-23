package com.matevskial.jh2patm.atm;

import com.matevskial.jh2patm.atm.Atm.State;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuView implements View {

  private final Atm atm;
  private final Keypad keypad;

  @Override
  public void display() {
    System.out.println("Main menu");
    State nextState = null;
    while (nextState == null) {
      System.out.printf("\t1 - View my balance%n");
      System.out.printf("\t2 - Withdraw cash%n");
      System.out.printf("\t3 - Deposit funds%n");
      System.out.printf("\t4 - Exit%n");
      String keypadInput = keypad.getInput();
      nextState = getNextState(keypadInput);
      if(nextState == null) {
        System.out.println("Invalid option selected " + keypadInput);
      }
    }
    atm.setState(nextState);
  }

  private State getNextState(String keypadInput) {
    return switch (keypadInput) {
      case "1" -> State.VIEW_BALANCE;
      case "2" -> State.WITHDRAW;
      case "3" -> State.DEPOSIT;
      case "4" -> State.WELCOME;
      default -> null;
    };
  }
}
