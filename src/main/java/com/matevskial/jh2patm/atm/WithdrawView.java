package com.matevskial.jh2patm.atm;

import com.matevskial.jh2patm.bank.BankAccountInsuficientBalance;
import com.matevskial.jh2patm.bank.BankService;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class WithdrawView implements View {

  private final Atm atm;
  private final BankService bankService;
  private final Keypad keypad;
  private final WithdrawSlot withdrawSlot;

  @Override
  public void display() {
    AtmState nextState = null;
    while (nextState == null) {
      displayMenu();
      String keypadInput = keypad.getNumericInput();
      OperationResult operationResult = performOperation(keypadInput);
      if(operationResult == OperationResult.TRANSACTION_CANCELED) {
        nextState = AtmState.MENU;
      } else if(operationResult == OperationResult.WITHDRAWAL_SUCCESSFUL) {
        nextState = AtmState.MENU;
      } else if(operationResult == OperationResult.WITHDRAWAL_FAILED) {
        System.out.println("Please choose a smaller amount");
      } else if(operationResult == OperationResult.NO_OPERATION_PERFORMED) {
        System.out.println("Invalid option selected " + keypadInput);
      }
    }
    atm.setState(nextState);
  }

  private OperationResult performOperation(String keypadInput) {
    if(isCancelTransactionOptionSelected(keypadInput)) {
      return OperationResult.TRANSACTION_CANCELED;
    } else if(isValidWithdrawAmountSelected(keypadInput)) {
      boolean withdraw = withdrawSelectedAmount(keypadInput);
      if(withdraw) {
        return OperationResult.WITHDRAWAL_SUCCESSFUL;
      } else {
        return OperationResult.WITHDRAWAL_FAILED;
      }
    } else {
      return OperationResult.NO_OPERATION_PERFORMED;
    }
  }

  private boolean withdrawSelectedAmount(String keypadInput) {
    BigDecimal amountToWithdraw = getAmountToWithdraw(keypadInput);
    try {
      checkAtmCash(amountToWithdraw);
      bankService.withdraw(atm.getUserSession().getAccountId(), amountToWithdraw);
      System.out.println("Please take your money");
      withdrawSlot.completeWithdraw();
      return true;
    } catch (AtmInsufficientCash | BankAccountInsuficientBalance e) {
      return false;
    }
  }

  private void checkAtmCash(BigDecimal amountToWithdraw) {
    if(amountToWithdraw.compareTo(atm.getCash()) > 0) {
      throw new AtmInsufficientCash();
    }
  }

  private BigDecimal getAmountToWithdraw(String keypadInput) {
    return switch (keypadInput) {
      case "1" -> new BigDecimal("20");
      case "2" -> new BigDecimal("40");
      case "3" -> new BigDecimal("60");
      case "4" -> new BigDecimal("10");
      case "5" -> new BigDecimal("200");
      default -> new BigDecimal("0");
    };
  }

  private boolean isValidWithdrawAmountSelected(String keypadInput) {
    return "1".equals(keypadInput)
        || "2".equals(keypadInput)
        || "3".equals(keypadInput)
        || "4".equals(keypadInput)
        || "5".equals(keypadInput);
  }

  private boolean isCancelTransactionOptionSelected(String keypadInput) {
    return "6".equals(keypadInput);
  }

  private void displayMenu() {
    System.out.printf("\t1 - $20\t\t4 - $100%n");
    System.out.printf("\t2 - $40\t\t5 - $200%n");
    System.out.printf("\t3 - $60\t\t6 - Cancel transaction%n");
    System.out.println("Choose a withdrawal amount: ");
  }

  private enum OperationResult {
    TRANSACTION_CANCELED,
    WITHDRAWAL_SUCCESSFUL,
    WITHDRAWAL_FAILED,
    NO_OPERATION_PERFORMED
  }
}
