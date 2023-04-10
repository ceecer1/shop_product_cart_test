package com.example.domain;

import com.example.CounterApi;
import com.google.protobuf.Empty;
import kalix.javasdk.testkit.ValueEntityResult;
import kalix.javasdk.valueentity.ValueEntity;
import org.junit.Ignore;
import org.junit.Test;

import java.util.UUID;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class CounterTest {

  @Test
  @Ignore("to be implemented")
  public void exampleTest() {
    CounterTestKit service = CounterTestKit.of(Counter::new);
    // // use the testkit to execute a command
    // // of events emitted, or a final updated state:
    // SomeCommand command = SomeCommand.newBuilder()...build();
    // ValueEntityResult<SomeResponse> result = service.someOperation(command);
    // // verify the reply
    // SomeReply reply = result.getReply();
    // assertEquals(expectedReply, reply);
    // // verify the final state after the command
    // assertEquals(expectedState, service.getState());
  }

  @Test
  public void increaseTest() {
    CounterTestKit service = CounterTestKit.of(Counter::new);
    CounterApi.IncreaseValue incCommand = CounterApi.IncreaseValue
            .newBuilder()
            .setCounterId("foo")
            .setValue(1)
            .build();

    ValueEntityResult<Empty> result = service.increase(incCommand);
    assertEquals(Empty.getDefaultInstance(), result.getReply());
    assertEquals(1, service.getState().getValue());
  }


  @Test
  public void decreaseTest() {
    CounterTestKit service = CounterTestKit.of(Counter::new);
    String id = UUID.randomUUID().toString();
    CounterApi.IncreaseValue increaseCmd = CounterApi.IncreaseValue.newBuilder()
            .setCounterId(id)
            .setValue(2).build();
    CounterApi.DecreaseValue decreaseCmd = CounterApi.DecreaseValue.newBuilder()
            .setCounterId(id)
            .setValue(3).build();
    ValueEntityResult<Empty> result1 = service.increase(increaseCmd);
    ValueEntityResult<Empty> result2 = service.decrease(decreaseCmd);
    assertEquals(Empty.getDefaultInstance(), result1.getReply());
    assertEquals(-1, service.getState().getValue());
  }


  @Test
  @Ignore("to be implemented")
  public void resetTest() {
    CounterTestKit service = CounterTestKit.of(Counter::new);
    // ResetValue command = ResetValue.newBuilder()...build();
    // ValueEntityResult<Empty> result = service.reset(command);
  }


  @Test
  @Ignore("to be implemented")
  public void getCurrentCounterTest() {
    CounterTestKit service = CounterTestKit.of(Counter::new);
    // GetCounter command = GetCounter.newBuilder()...build();
    // ValueEntityResult<CurrentCounter> result = service.getCurrentCounter(command);
  }

}
