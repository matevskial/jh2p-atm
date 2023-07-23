package com.matevskial.jh2patm.bank;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserAuthenticationResponse {

  String accountId;

  public boolean isAuthenticated() {
    return accountId != null;
  }
}
