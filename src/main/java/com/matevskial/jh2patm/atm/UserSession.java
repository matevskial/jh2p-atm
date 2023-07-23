package com.matevskial.jh2patm.atm;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class UserSession {

  String accountId;
}
