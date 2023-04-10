package com.example.view;

import com.example.api.ShoppingCartApi;
import com.example.domain.ShoppingCartDomain;
import kalix.javasdk.view.View;
import kalix.javasdk.view.ViewContext;

import java.util.logging.Logger;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the View Service described in your com/example/views/shoppingcart_view.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class EventsByIdView extends AbstractEventsByIdView {

  private static Logger logger = Logger.getLogger("EventsByIdView");

  public EventsByIdView(ViewContext context) {}

  @Override
  public ShoppingCartApi.ItemAddedView emptyState() {
    return null;
  }

  @Override
  public UpdateEffect<ShoppingCartApi.ItemAddedView> processItemAdded(ShoppingCartApi.ItemAddedView state, ShoppingCartDomain.ItemAdded itemAdded) {
    logger.info("###########################");
    logger.info("Reached the view part processItemAdded with id: " + itemAdded.getId());
    logger.info("State id: " + (state == null ? "undefined": state.getId()));
    logger.info("###########################");
    return effects().updateState(
          ShoppingCartApi.ItemAddedView.newBuilder()
                  .setId(itemAdded.getId())
                  .setItem(ShoppingCartApi.LineItem.newBuilder()
                          .setProductId(itemAdded.getItem().getProductId())
                          .setName(itemAdded.getItem().getName())
                          .setQuantity(itemAdded.getItem().getQuantity() + (state == null ? 0 : state.getItem().getQuantity()))
                          .build())
                  .build());
  }
}

