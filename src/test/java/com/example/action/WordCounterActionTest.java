package com.example.action;

import akka.stream.javadsl.Source;
import com.example.action.WordCounterAction;
import com.example.action.WordCounterActionTestKit;
import com.example.action.WordCounterApi;
import kalix.javasdk.testkit.ActionResult;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class WordCounterActionTest {

  @Test
  @Ignore("to be implemented")
  public void exampleTest() {
    WordCounterActionTestKit service = WordCounterActionTestKit.of(WordCounterAction::new);
    // // use the testkit to execute a command
    // SomeCommand command = SomeCommand.newBuilder()...build();
    // ActionResult<SomeResponse> result = service.someOperation(command);
    // // verify the reply
    // SomeReply reply = result.getReply();
    // assertEquals(expectedReply, reply);
  }

  @Test
  @Ignore("to be implemented")
  public void countWordsTest() {
    WordCounterActionTestKit testKit = WordCounterActionTestKit.of(WordCounterAction::new);
    // ActionResult<WordCounterApi.Result> result = testKit.countWords(WordCounterApi.Text.newBuilder()...build());
  }

}
