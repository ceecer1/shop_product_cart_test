package com.example.action;

import com.example.api.ShoppingCartApi;
import com.google.protobuf.Empty;
import kalix.javasdk.DeferredCall;
import kalix.javasdk.action.ActionCreationContext;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Action Service described in your com/example/action/shoppingcart_controller.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShoppingCartControllerAction extends AbstractShoppingCartControllerAction {

  public ShoppingCartControllerAction(ActionCreationContext creationContext) {}

  @Override
  public Effect<Empty> verifyAddItem(ShoppingCartApi.AddLineItem addLineItem) {
    if (addLineItem.getName().equalsIgnoreCase("carrot")) {
      return effects().error("Carrots no longer for sale");
    } else {
      DeferredCall<ShoppingCartApi.AddLineItem, Empty> call =
              components().shoppingCart().addItem(addLineItem);
      return effects().forward(call);
    }
  }
}
