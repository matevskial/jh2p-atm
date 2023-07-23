package com.matevskial.jh2patm.atm;

public class AtmInsufficientCash extends RuntimeException {

  public AtmInsufficientCash() {
    super("Atm has insufficient cash");
  }
}
