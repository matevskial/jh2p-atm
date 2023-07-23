package com.matevskial.jh2patm.atm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import lombok.RequiredArgsConstructor;

public class FakeDepositSlot implements DepositSlot {

  private static final long DEPOSIT_TIMEOUT_SECONDS = 5L;

  @Override
  public void receiveEnvelope() {
    String input = null;
    try {
      input = new ConsoleInputWithTimeout(DEPOSIT_TIMEOUT_SECONDS)
          .readLine("(Simulate deposit slot) Press enter to insert envelope");
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new RuntimeException(e);
    }

    if(input == null) {
      throw new DepositSlotInactivityException();
    }
  }

  private static class ConsoleInputWithTimeout {

    private final long timeoutInSeconds;

    public ConsoleInputWithTimeout(long timeoutInSeconds) {
      this.timeoutInSeconds = timeoutInSeconds;
    }

    public String readLine(String prompt) throws InterruptedException {
      ExecutorService ex = Executors.newSingleThreadExecutor();
      String input = null;
      Future<String> result = ex.submit(new ConsoleInputReadTask(prompt));
      try {
        input = result.get(timeoutInSeconds, TimeUnit.SECONDS);
      } catch (ExecutionException e) {
        throw new RuntimeException(e);
      } catch (TimeoutException e) {
        result.cancel(true);
      } finally {
        ex.shutdownNow();
      }
      return input;
    }
  }

  @RequiredArgsConstructor
  private static class ConsoleInputReadTask implements Callable<String> {
    private final String prompt;

    public String call() throws IOException {
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      String input;
      System.out.println(prompt);
      try {
        // wait until we have data to complete a readLine()
        while (!br.ready()) {
          Thread.sleep(200);
        }
        input = br.readLine();
      } catch (InterruptedException e) {
        return null;
      }
      return input;
    }
  }
}
