package com.example.domain;

import com.example.api.ProductApi;
import com.google.protobuf.Empty;
import kalix.javasdk.eventsourcedentity.EventSourcedEntity;
import kalix.javasdk.eventsourcedentity.EventSourcedEntity.Effect;
import kalix.javasdk.eventsourcedentity.EventSourcedEntityContext;

import java.util.logging.Logger;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Event Sourced Entity Service described in your com/example/api/product_api.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ProductStateEntity extends AbstractProductStateEntity {


  private static Logger logger = Logger.getLogger("ProductStateEntity");
  @SuppressWarnings("unused")
  private final String entityId;

  public ProductStateEntity(EventSourcedEntityContext context) {
    this.entityId = context.entityId();
  }

  @Override
  public ProductDomain.ProductState emptyState() {
    return ProductDomain.ProductState.getDefaultInstance();
  }

  @Override
  public Effect<ProductDomain.ProductState> expire(ProductDomain.ProductState currentState, ProductApi.ExpireProduct expireProduct) {
    logger.info("Product entity expire : " + expireProduct.toString());
    ProductDomain.ProductExpired expiredEvent = ProductDomain.ProductExpired.newBuilder()
            .setId(expireProduct.getId()).build();
    return effects().emitEvent(expiredEvent).thenReply(r -> currentState.toBuilder().setStatus(false).build());
  }

  @Override
  public Effect<Empty> create(ProductDomain.ProductState currentState, ProductApi.CreateProductRequest createProductRequest) {
    logger.info("CREATE PRODUCT REQUEST AT ProductStateEntity");
    ProductDomain.ProductCreated createdEvent = ProductDomain.ProductCreated.newBuilder()
            .setProduct(ProductDomain.ProductState.newBuilder()
                    .setId(createProductRequest.getId())
                    .setName(createProductRequest.getName())
                    .setSupplier(createProductRequest.getSupplier())
                    .setStatus(true))
            .build();
    return effects().emitEvent(createdEvent).thenReply(r -> Empty.getDefaultInstance());
  }

  @Override
  public Effect<ProductDomain.ProductState> get(ProductDomain.ProductState currentState, ProductApi.GetProductRequest getProductRequest) {
    return effects().reply(currentState);
  }

  @Override
  public ProductDomain.ProductState productCreated(ProductDomain.ProductState currentState, ProductDomain.ProductCreated productCreated) {
    logger.info("PRODUCT CREATE EVENT HANDLER START");
    if(currentState != null)
      logger.info("Product current state is not null, id is : " + currentState.getId());
    logger.info("ProductCreated event received, id is : " + productCreated.getProduct().getId());
    logger.info("PRODUCT CREATE EVENT HANDLER END");

    return ProductDomain.ProductState.newBuilder()
            .setId(productCreated.getProduct().getId())
            .setName(productCreated.getProduct().getName())
            .setStatus(true)
            .setSupplier(productCreated.getProduct().getSupplier())
            .build();

  }

  @Override
  public ProductDomain.ProductState productExpired(ProductDomain.ProductState currentState, ProductDomain.ProductExpired productExpired) {
    return currentState.toBuilder().setStatus(false).build();
  }

}
