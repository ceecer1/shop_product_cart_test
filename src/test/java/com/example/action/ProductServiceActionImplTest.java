package com.example.action;

import akka.stream.javadsl.Source;
import com.example.action.ProductControllerApi;
import com.example.action.ProductServiceActionImpl;
import com.example.action.ProductServiceActionImplTestKit;
import com.google.protobuf.Empty;
import kalix.javasdk.testkit.ActionResult;
import org.junit.Ignore;
import org.junit.Test;
import static org.junit.Assert.*;

// This class was initially generated based on the .proto definition by Kalix tooling.
//
// As long as this file exists it will not be overwritten: you can maintain it yourself,
// or delete it so it is regenerated as needed.

public class ProductServiceActionImplTest {

  @Test
  @Ignore("to be implemented")
  public void exampleTest() {
    ProductServiceActionImplTestKit service = ProductServiceActionImplTestKit.of(ProductServiceActionImpl::new);
    // // use the testkit to execute a command
    // SomeCommand command = SomeCommand.newBuilder()...build();
    // ActionResult<SomeResponse> result = service.someOperation(command);
    // // verify the reply
    // SomeReply reply = result.getReply();
    // assertEquals(expectedReply, reply);
  }

  @Test
  @Ignore("to be implemented")
  public void createProductTest() {
    ProductServiceActionImplTestKit testKit = ProductServiceActionImplTestKit.of(ProductServiceActionImpl::new);
    // ActionResult<Empty> result = testKit.createProduct(ProductControllerApi.CreateProductRequest.newBuilder()...build());
  }

}
