package com.matevskial.jh2patm.atm;

import java.util.Scanner;

public class StandardIoKeypad implements Keypad {

  private final Scanner in = new Scanner(System.in);

  @Override
  public String getInput() {
    return in.nextLine();
  }

  @Override
  public String getNumericInput() {
    String numericInput = in.nextLine();
    while(!isValidNumericInput(numericInput)) {
      System.out.println("Enter valid numeric input");
      numericInput = in.nextLine();
    }
    return numericInput;
  }

  private boolean isValidNumericInput(String keypadInput) {
    return keypadInput.matches("\\d+");
  }
}
