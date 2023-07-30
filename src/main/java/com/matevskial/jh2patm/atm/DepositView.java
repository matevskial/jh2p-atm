package com.matevskial.jh2patm.atm;

import com.matevskial.jh2patm.bank.BankDepositException;
import com.matevskial.jh2patm.bank.BankService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DepositView implements View {

  private final Atm atm;
  private final BankService bankService;
  private final Keypad keypad;
  private final DepositSlot depositSlot;

  @Override
  public void display() {
    System.out.println("Enter deposit amount(or type 0 to cancel)");
    String keypadInput = keypad.getInput();
    OperationResult operationResult = performOperation(keypadInput);
    if (operationResult.equals(OperationResult.OPERATION_CANCELED)) {
      System.out.println("Deposit canceled");
      atm.setState(AtmState.MENU);
    } else if(operationResult.equals(OperationResult.DEPOSIT_SUCCESSFUL)) {
      System.out.println("Deposit successful");
      atm.setState(AtmState.MENU);
    } else if(operationResult.equals(OperationResult.DEPOSIT_FAILED)) {
      System.out.println("Deposit failed");
      atm.setState(AtmState.MENU);
    } else if(operationResult.equals(OperationResult.DEPOSIT_SLOT_INACTIVITY)) {
      System.out.println("Transaction canceled due to inactivity");
      atm.setState(AtmState.MENU);
    }
  }

  private OperationResult performOperation(String keypadInput) {
    validateInput(keypadInput);

    if("0".equals(keypadInput)) {
      return OperationResult.OPERATION_CANCELED;
    }

    BigDecimal amountToDeposit = new BigDecimal(keypadInput).divide(new BigDecimal("100"));
    try {
      System.out.println("Insert envelope");
      depositSlot.receiveEnvelope();
    } catch (DepositSlotInactivityException e) {
      return OperationResult.DEPOSIT_SLOT_INACTIVITY;
    }

    try {
      System.out.println("Depositing the amount");
      bankService.deposit(atm.getUserSession().getAccountId(), amountToDeposit);
      return OperationResult.DEPOSIT_SUCCESSFUL;
    } catch (BankDepositException e) {
      return OperationResult.DEPOSIT_FAILED;
    }
  }

  private boolean validateInput(String keypadInput) {
    return keypadInput.matches("\\d+");
  }

  private enum OperationResult {
    OPERATION_CANCELED,
    DEPOSIT_SUCCESSFUL,
    DEPOSIT_SLOT_INACTIVITY,
    DEPOSIT_FAILED
  }
}
