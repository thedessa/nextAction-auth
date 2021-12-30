package dev.ifrs.resource;

import io.quarkus.oidc.token.propagation.AccessToken;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import dev.ifrs.model.NextAction;

@RegisterRestClient(baseUri = "http://localhost:8081/nextAction")
@AccessToken
public interface MyRestClient {

  @GET
  @Path("list/{userId}")
  @Produces(MediaType.APPLICATION_JSON)
  List<NextAction> list(@PathParam("userId") final String userId);

  @POST
  @Path("add/{userId}/{type}/{title}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Response add(@PathParam("userId") final String userId,
               @PathParam("type") final int type,
               @PathParam("title") final String title);

  @POST
  @Path("complete/{userId}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Response complete(@PathParam("userId") final String userId,
                    @PathParam("taskId") final String taskId);

  @POST
  @Path("rename/{userId}/{taskId}/{newTitle}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Response rename(@PathParam("userId") final String userId,
                  @PathParam("taskId") final String taskId,
                  @PathParam("newTitle") final String newTitle);

  @POST
  @Path("context/{userId}/{taskId}/{newContext}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Response context(@PathParam("userId") final String userId,
                   @PathParam("taskId") final String taskId,
                   @PathParam("newContext") final String newContext);

  @POST
  @Path("delete/{userId}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  Response delete(@PathParam("userId") final String userId,
                  @PathParam("taskId") final String taskId);

}