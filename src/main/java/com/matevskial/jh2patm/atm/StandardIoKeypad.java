package com.matevskial.jh2patm.atm;

import java.util.Scanner;

public class StandardIoKeypad implements Keypad {

  private final Scanner in = new Scanner(System.in);

  @Override
  public String getInput() {
    return in.nextLine();
  }
}
