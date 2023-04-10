package com.example.domain;

import com.example.api.ShoppingCartApi;
import com.google.protobuf.Empty;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Event Sourced Entity Service described in your com/example/api/shoppingcart_api.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShoppingCart extends AbstractShoppingCart {

  private static Logger logger = Logger.getLogger("ShoppingCart");

  @SuppressWarnings("unused")
  private final String entityId;

  public ShoppingCart(EventSourcedEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public ShoppingCartDomain.Cart emptyState() {
    return ShoppingCartDomain.Cart.getDefaultInstance();
  }

  @Override
  public Effect<Empty> addItem(ShoppingCartDomain.Cart currentState, ShoppingCartApi.AddLineItem addLineItem) {
    if(currentState.getCheckedOut()) {
      return effects().error("Cart is already checked out.");
    }
    if(addLineItem.getQuantity() <= 0) {
      return effects().error("Quantity for item " + addLineItem.getProductId() + " must be greater than 0.");
    }

    // just emits the ItemAdded event and returns
    ShoppingCartDomain.ItemAdded itemAddedEvent = ShoppingCartDomain.ItemAdded
            .newBuilder()
            .setItem(
                    ShoppingCartDomain.LineItem.newBuilder()
                            .setProductId(addLineItem.getProductId())
                            .setName(addLineItem.getName())
                            .setQuantity(addLineItem.getQuantity())
                            .build())
            .setId(addLineItem.getCartId())
            .build();
    return effects().emitEvent(itemAddedEvent).thenReply(newState -> {
      logger.info("NEW STATE BEGIN");
      logger.info(newState.toString());
      logger.info("NEW STATE END");
      return Empty.getDefaultInstance();
    });
  }

  @Override
  public Effect<Empty> removeItem(ShoppingCartDomain.Cart currentState, ShoppingCartApi.RemoveLineItem removeLineItem) {
    if(currentState.getCheckedOut()) {
      return effects().error("Cart is already checked out.");
    }

    ShoppingCartDomain.ItemRemoved itemRemovedEvent = ShoppingCartDomain.ItemRemoved.newBuilder()
            .setId(removeLineItem.getCartId())
            .setProductId(removeLineItem.getProductId())
            .build();

    return effects().emitEvent(itemRemovedEvent).thenReply(newState -> Empty.getDefaultInstance());

  }

  @Override
  public Effect<ShoppingCartApi.Cart> getCart(ShoppingCartDomain.Cart currentState, ShoppingCartApi.GetShoppingCart getShoppingCart) {
    ShoppingCartApi.Cart cart = ShoppingCartApi.Cart.newBuilder()
            .addAllItems(currentState.getItemsList().stream().map(i ->
                    ShoppingCartApi.LineItem.newBuilder()
                            .setProductId(i.getProductId())
                            .setName(i.getName())
                            .setQuantity(i.getQuantity())
                            .build()).collect(Collectors.toList()))
            .build();
    return effects().reply(cart);
  }

  @Override
  public Effect<Empty> checkout(ShoppingCartDomain.Cart currentState, ShoppingCartApi.CheckoutShoppingCart checkoutShoppingCart) {
    if (currentState.getCheckedOut()) {
      return effects().error("Cart is already checked out.");
    }
    return effects()
            .emitEvent(ShoppingCartDomain.CheckedOut.getDefaultInstance())
            .deleteEntity()
            .thenReply(newState -> Empty.getDefaultInstance());
  }

  @Override
  public Effect<Empty> removeCart(ShoppingCartDomain.Cart currentState, ShoppingCartApi.RemoveShoppingCart removeShoppingCart) {
    return null;
  }

  @Override
  public ShoppingCartDomain.Cart itemAdded(ShoppingCartDomain.Cart currentState, ShoppingCartDomain.ItemAdded itemAdded) {

    logger.info("$$$$$$$$$$$$$$$$$$$");
    // gets added item from event
    ShoppingCartDomain.LineItem item = itemAdded.getItem();

    // if item already present on cart, and if item's product id matches, add new quantity value on it and return the item
    ShoppingCartDomain.LineItem lineItem = updateItem(item, currentState);

    // Get line items from cart by removing that product id
    List<ShoppingCartDomain.LineItem> lineItems = removeItemByProductId(currentState, item.getProductId());

    // add the updated line item to the line items list
    lineItems.add(lineItem);

    // sort items
    lineItems.sort(Comparator.comparing(ShoppingCartDomain.LineItem::getProductId));

    //return the shopping cart
    return  ShoppingCartDomain.Cart.newBuilder().addAllItems(lineItems).setId(entityId).build();
  }

  private ShoppingCartDomain.LineItem updateItem(
          ShoppingCartDomain.LineItem item, ShoppingCartDomain.Cart cart) {
    return findItemByProductId(cart, item.getProductId())
            .map(li -> li.toBuilder().setQuantity(li.getQuantity() + item.getQuantity()).build()).orElse(item);
  }

  private Optional<ShoppingCartDomain.LineItem> findItemByProductId(
          ShoppingCartDomain.Cart cart, String productId) {
    Predicate<ShoppingCartDomain.LineItem> lineItemExists =
            lineItem -> lineItem.getProductId().equals(productId);
    return cart.getItemsList().stream().filter(lineItemExists).findFirst();
  }

  private List<ShoppingCartDomain.LineItem> removeItemByProductId(
          ShoppingCartDomain.Cart cart, String productId) {
    return cart.getItemsList().stream()
            .filter(lineItem -> !lineItem.getProductId().equals(productId))
            .collect(Collectors.toList());
  }
  @Override
  public ShoppingCartDomain.Cart itemRemoved(ShoppingCartDomain.Cart currentState, ShoppingCartDomain.ItemRemoved itemRemoved) {
    List<ShoppingCartDomain.LineItem> lineItems = removeItemByProductId(currentState, itemRemoved.getProductId());
    lineItems.sort(Comparator.comparing(ShoppingCartDomain.LineItem::getProductId));

    //return the shopping cart
    return  ShoppingCartDomain.Cart.newBuilder().addAllItems(lineItems).setId(entityId).build();
  }
  @Override
  public ShoppingCartDomain.Cart checkedOut(ShoppingCartDomain.Cart currentState, ShoppingCartDomain.CheckedOut checkedOut) {
    return ShoppingCartDomain.Cart.getDefaultInstance();
  }

}
