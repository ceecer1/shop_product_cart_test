package com.example.domain;

import com.example.api.ShoppingCartApi;
import com.google.protobuf.Empty;
import kalix.javasdk.eventsourcedentity.EventSourcedEntity;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;
import kalix.javasdk.testkit.EventSourcedResult;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ShoppingCartTest {

  @Test
  public void exampleTest() {
    ShoppingCartTestKit service = ShoppingCartTestKit.of(ShoppingCart::new);
    ShoppingCartApi.AddLineItem apples = ShoppingCartApi.AddLineItem.newBuilder()
            .setProductId("idA")
            .setName("Apples")
            .setQuantity(7)
            .build();
    EventSourcedResult<Empty> result = service.addItem(apples);
    ShoppingCartApi.AddLineItem bananas = ShoppingCartApi.AddLineItem.newBuilder()
            .setProductId("idB")
            .setName("Bananas")
            .setQuantity(13)
            .build();
    EventSourcedResult<Empty> result1 = service.addItem(bananas);
    assertEquals(1, result.getAllEvents().size());
    assertEquals(2, service.getAllEvents().size());

    ShoppingCartDomain.ItemAdded applesAdded = result.getNextEventOfType(ShoppingCartDomain.ItemAdded.class);
    assertEquals("Apples", applesAdded.getItem().getName());

    ShoppingCartDomain.ItemAdded bananasAdded = result1.getNextEventOfType(ShoppingCartDomain.ItemAdded.class);
    assertEquals("Bananas", bananasAdded.getItem().getName());

    assertEquals(service.getState().getItemsCount(), 2);
  }

  @Test
  @Ignore("to be implemented")
  public void addItemTest() {
    ShoppingCartTestKit service = ShoppingCartTestKit.of(ShoppingCart::new);
    // AddLineItem command = AddLineItem.newBuilder()...build();
    // EventSourcedResult<Empty> result = service.addItem(command);
  }


  @Test
  @Ignore("to be implemented")
  public void removeItemTest() {
    ShoppingCartTestKit service = ShoppingCartTestKit.of(ShoppingCart::new);
    // RemoveLineItem command = RemoveLineItem.newBuilder()...build();
    // EventSourcedResult<Empty> result = service.removeItem(command);
  }


  @Test
  @Ignore("to be implemented")
  public void getCartTest() {
    ShoppingCartTestKit service = ShoppingCartTestKit.of(ShoppingCart::new);
    // GetShoppingCart command = GetShoppingCart.newBuilder()...build();
    // EventSourcedResult<Cart> result = service.getCart(command);
  }


  @Test
  @Ignore("to be implemented")
  public void checkoutTest() {
    ShoppingCartTestKit service = ShoppingCartTestKit.of(ShoppingCart::new);
    // CheckoutShoppingCart command = CheckoutShoppingCart.newBuilder()...build();
    // EventSourcedResult<Empty> result = service.checkout(command);
  }

}
