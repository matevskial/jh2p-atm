package com.matevskial.jh2patm.atm;

import com.matevskial.jh2patm.bank.BankService;
import com.matevskial.jh2patm.bank.UserAuthenticationInput;
import com.matevskial.jh2patm.bank.UserAuthenticationResponse;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WelcomeView implements View {

  private final Atm atm;
  private final BankService bankService;
  private final Keypad keypad;

  @Override
  public void display() {
    System.out.println("Welcome!");
    authenticateUser();
  }

  private void authenticateUser() {
    boolean authenticated = false;
    while (!authenticated) {
      UserAuthenticationInput.UserAuthenticationInputBuilder userAuthenticationInputBuilder = UserAuthenticationInput.builder();
      System.out.println("Please enter your account number: ");
      userAuthenticationInputBuilder.accountNumber(keypad.getInput());
      System.out.println("Enter your PIN: ");
      userAuthenticationInputBuilder.pin(keypad.getInput());
      UserAuthenticationResponse userAuthenticationResponse = bankService.authenticateUser(userAuthenticationInputBuilder.build());
      authenticated = userAuthenticationResponse.isAuthenticated();
      if(!authenticated) {
        try {
          System.out.println("Invalid credentials, either your account number or PIN is wrong");
          Thread.sleep(1000);
        } catch (InterruptedException e) {
          Thread.currentThread().interrupt();
          throw new RuntimeException(e);
        }
      } else {
        atm.setState(AtmState.MENU);
        atm.setUserSession(UserSession.builder().accountId(userAuthenticationResponse.getAccountId()).build());
      }
    }
  }
}
