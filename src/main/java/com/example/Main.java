package com.example;

import com.example.action.*;
import com.example.domain.*;
import com.example.view.EventsByIdView;
import com.example.view.EventsByIdViewProvider;
import kalix.javasdk.Kalix;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public final class Main {

  private static final Logger LOG = LoggerFactory.getLogger(Main.class);

  public static Kalix createKalix() {
    // The KalixFactory automatically registers any generated Actions, Views or Entities,
    // and is kept up-to-date with any changes in your protobuf definitions.
    // If you prefer, you may remove this and manually register these components in a
    // `new Kalix()` instance.

    Kalix kalix = new Kalix();
    kalix.register(ShoppingCartProvider.of(ShoppingCart::new));
    kalix.register(CounterProvider.of(Counter::new));
    kalix.register(EventsByIdViewProvider.of(EventsByIdView::new));
    kalix.register(ShoppingCartControllerActionProvider.of(ShoppingCartControllerAction::new));
    kalix.register(WordCounterActionProvider.of(WordCounterAction::new));
    kalix.register(ProductServiceActionProvider.of(ProductServiceActionImpl::new));
    kalix.register(ProductStateEntityProvider.of(ProductStateEntity::new));
    return kalix;
//    return KalixFactory.withComponents(
//            Counter::new,
//            ShoppingCart::new);
  }

  public static void main(String[] args) throws Exception {
    LOG.info("starting the Kalix service");
    createKalix().start();
  }
}
