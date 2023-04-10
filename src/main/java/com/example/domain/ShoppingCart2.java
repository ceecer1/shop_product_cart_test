//package com.example.domain;
//
//import com.example.api.ShoppingCartApi;
//import com.google.protobuf.Empty;
//import kalix.javasdk.replicatedentity.ReplicatedCounterMap;
//import kalix.javasdk.replicatedentity.ReplicatedEntityContext;
//
//import java.util.Comparator;
//import java.util.List;
//import java.util.stream.Collectors;
//
//// This class was initially generated based on the .proto definition by Kalix tooling.
//// This is the implementation for the Replicated Entity Service described in your com/example/api/shoppingcart_api.proto file.
////
//// As long as this file exists it will not be overwritten: you can maintain it yourself,
//// or delete it so it is regenerated as needed.
//
//public class ShoppingCart extends AbstractShoppingCart {
//  @SuppressWarnings("unused")
//  private final String entityId;
//
//  public ShoppingCart(ReplicatedEntityContext context) {
//    this.entityId = context.entityId();
//  }
//
//  @Override
//  public Effect<Empty> addItem(ReplicatedCounterMap<ShoppingCartDomain.Product> currentData, ShoppingCartApi.AddLineItem addLineItem) {
//    if (addLineItem.getQuantity() <= 0) {
//      return effects().error("Quantity for item " + addLineItem.getProductId() + " must be greater than zero.");
//    }
//
//    ShoppingCartDomain.Product product = ShoppingCartDomain.Product.newBuilder()
//            .setId(addLineItem.getProductId())
//            .setName(addLineItem.getName())
//            .build();
//
//    ReplicatedCounterMap<ShoppingCartDomain.Product> updatedCart =
//            currentData.increment(product, addLineItem.getQuantity());
//
//    return effects().update(updatedCart).thenReply(Empty.getDefaultInstance());
//  }
//
//  @Override
//  public Effect<Empty> removeItem(ReplicatedCounterMap<ShoppingCartDomain.Product> currentData, ShoppingCartApi.RemoveLineItem removeLineItem) {
//    return effects().error("The command handler for `RemoveItem` is not implemented, yet");
//  }
//
//  @Override
//  public Effect<ShoppingCartApi.Cart> getCart(ReplicatedCounterMap<ShoppingCartDomain.Product> currentData, ShoppingCartApi.GetShoppingCart getShoppingCart) {
//    List<ShoppingCartApi.LineItem> itemList = currentData.keySet().stream()
//            .map(product ->
//                  ShoppingCartApi.LineItem.newBuilder()
//                    .setName(product.getName())
//                    .setProductId(product.getId())
//                    .setQuantity((int) currentData.get(product))
//                    .build())
//            .sorted(Comparator.comparing(ShoppingCartApi.LineItem::getProductId))
//            .collect(Collectors.toList());
//    ShoppingCartApi.Cart cart = ShoppingCartApi.Cart.newBuilder().addAllItems(itemList).build();
//    return effects().reply(cart);
//  }
//
//  @Override
//  public Effect<Empty> checkout(ReplicatedCounterMap<ShoppingCartDomain.Product> currentData, ShoppingCartApi.CheckoutShoppingCart checkoutShoppingCart) {
//    return effects().error("The command handler for `Checkout` is not implemented, yet");
//  }
//
//  @Override
//  public Effect<Empty> removeCart(ReplicatedCounterMap<ShoppingCartDomain.Product> currentData, ShoppingCartApi.RemoveShoppingCart removeShoppingCart) {
//    return effects().delete().thenReply(Empty.getDefaultInstance());
//  }
//}
