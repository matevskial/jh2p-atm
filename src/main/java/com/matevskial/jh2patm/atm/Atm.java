package com.matevskial.jh2patm.atm;

import com.matevskial.jh2patm.bank.BankService;
import java.math.BigDecimal;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class Atm {

  private final BankService bankService;
  private final Keypad keypad;
  private final DepositSlot depositSlot;
  private final WithdrawSlot withdrawSlot;

  private AtmState currentState = AtmState.WELCOME;

  @Getter
  private UserSession userSession;
  @Getter
  private BigDecimal cash = new BigDecimal("10000");

  public void run() {
    while(currentState != AtmState.SHUTDOWN_ATM) {
      View currentView = switch (currentState) {
        case WELCOME -> new WelcomeView(this, bankService, keypad);
        case MENU -> new MenuView(this, keypad);
        case VIEW_BALANCE -> new ViewBalanceView(this, bankService, keypad);
        case WITHDRAW -> new WithdrawView(this, bankService, keypad, withdrawSlot);
        case DEPOSIT -> new DepositView(this, bankService, keypad, depositSlot);
        case SHUTDOWN_ATM -> new ShutdownAtmView();
      };
      currentView.display();
    }
  }

  public void setState(AtmState state) {
    currentState = state;
  }

  void setUserSession(UserSession userSession) {
    this.userSession = userSession;
  }

}
