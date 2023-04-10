package com.example.action;

import akka.Done;
import com.example.api.ProductApi;
import com.example.domain.ProductDomain;
import com.google.protobuf.Empty;
import kalix.javasdk.action.ActionCreationContext;

import java.time.Duration;
import java.util.concurrent.CompletionStage;
import java.util.logging.Logger;

// This class was initially generated based on the .proto definition by Kalix tooling.
// This is the implementation for the Action Service described in your com/example/action/product_controller.proto file.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ProductServiceActionImpl extends AbstractProductServiceAction {

  private static Logger logger = Logger.getLogger("ProductServiceActionImpl");
  public ProductServiceActionImpl(ActionCreationContext creationContext) {}

  @Override
  public Effect<Empty> createAction(ProductControllerApi.Create create) {
    CompletionStage<Done> timerRegistration = timers().startSingleTimer(
            create.getId(), Duration.ofMinutes(1),
            components().productServiceActionImpl().expireAction(
                    ProductControllerApi.Expire.newBuilder().setId(create.getId()).build()));

    ProductApi.CreateProductRequest command = ProductApi.CreateProductRequest.newBuilder()
            .setId(create.getId())
            .setName(create.getName())
            .setSupplier(create.getSupplier())
            .build();

    return effects().asyncReply(
            timerRegistration.thenCompose(done -> components().productStateEntity().create(command).execute())
                    .thenApply(empty -> Empty.getDefaultInstance())
    );
  }

  @Override
  public Effect<ProductDomain.ProductState> expireAction(ProductControllerApi.Expire expire) {
    return effects().forward(components().productStateEntity().expire(
            ProductApi.ExpireProduct.newBuilder().setId(expire.getId()).build()
    ));
  }
}
