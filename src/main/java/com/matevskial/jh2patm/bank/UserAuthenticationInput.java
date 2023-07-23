package com.matevskial.jh2patm.bank;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserAuthenticationInput {

  String accountNumber;
  String pin;
}
