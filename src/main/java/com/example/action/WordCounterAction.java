package com.example.action;

import kalix.javasdk.action.ActionCreationContext;

import java.util.Arrays;
import java.util.logging.Logger;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Action Service described in your com/example/action/word_counter.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class WordCounterAction extends AbstractWordCounterAction {

  private static Logger logger = Logger.getLogger("WordCounterAction");
  public WordCounterAction(ActionCreationContext creationContext) {}

  @Override
  public Effect<WordCounterApi.Result> countWords(WordCounterApi.Text text) {
    String[] lines = text.getMessage().split("\n");
    int calc = Arrays.stream(lines)
            .map(line -> line.split(" "))
            .flatMap(Arrays::stream)
            .toArray(String[]::new)
            .length;
    WordCounterApi.Result result = WordCounterApi.Result.newBuilder()
            .setCount(calc).build();


    return effects().reply(result);
  }
}
