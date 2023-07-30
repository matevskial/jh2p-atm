package com.matevskial.jh2patm.atm;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MenuView implements View {

  private final Atm atm;
  private final Keypad keypad;

  @Override
  public void display() {
    System.out.println("Main menu");
    AtmState nextState = null;
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

  private AtmState getNextState(String keypadInput) {
    return switch (keypadInput) {
      case "1" -> AtmState.VIEW_BALANCE;
      case "2" -> AtmState.WITHDRAW;
      case "3" -> AtmState.DEPOSIT;
      case "4" -> AtmState.WELCOME;
      default -> null;
    };
  }
}
