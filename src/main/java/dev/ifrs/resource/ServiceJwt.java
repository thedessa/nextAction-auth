package dev.ifrs.resource;

import io.smallrye.jwt.build.Jwt;

import org.eclipse.microprofile.rest.client.inject.RestClient;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.SecurityContext;

import dev.ifrs.dao.UserDaoImpl;
import dev.ifrs.model.NextAction;
import dev.ifrs.model.UserResponse;

@Path("/jwt")
public class ServiceJwt {

  @Inject
  @RestClient
  MyRestClient service;

  @Inject
  UserDaoImpl dao;

  @GET
  @Path("list/{userId}")
  @RolesAllowed({"User"})
  @Produces(MediaType.APPLICATION_JSON)
  public List<NextAction> list(@PathParam("userId") final String userId) {
    return service.list(userId);
  }

  @POST
  @Path("add/{userId}/{type}/{title}")
  @RolesAllowed({"User"})
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response add(@PathParam("userId") final String userId,
                      @PathParam("type") final int type,
                      @PathParam("title") final String title) {
    return service.add(userId, type, title);
  }

  @POST
  @Path("complete/{userId}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response complete(@PathParam("userId") final String userId,
                           @PathParam("taskId") final String taskId) {
    return service.complete(userId, taskId);
  }

  @POST
  @Path("rename/{userId}/{taskId}/{newTitle}")
  @RolesAllowed({"User"})
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response rename(@PathParam("userId") final String userId,
                         @PathParam("taskId") final String taskId,
                         @PathParam("newTitle") final String newTitle) {
    return service.rename(userId, taskId, newTitle);
  }

  @POST
  @Path("context/{userId}/{taskId}/{newContext}")
  @RolesAllowed({"User"})
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response context(@PathParam("userId") final String userId,
                          @PathParam("taskId") final String taskId,
                          @PathParam("newContext") final String newContext) {
    return service.context(userId, taskId, newContext);
  }

  @POST
  @Path("delete/{userId}/{taskId}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("userId") final String userId,
                         @PathParam("taskId") final String taskId) {
    return service.delete(userId, taskId);
  }


  @GET
  @Path("/gen/{email}/{password}")
  @PermitAll
  @Produces(MediaType.TEXT_PLAIN)
  public UserResponse generate(@Context SecurityContext ctx,
                               @PathParam("email") final String email,
                               @PathParam("password") final String password) {
    try {
      final String userId = dao.authUser(email, password);
      return new UserResponse(Jwt.issuer("http://localhost:8080")
          .upn(userId)
          .groups(new HashSet<>(Collections.singletonList("User")))
          .sign(), userId);
    } catch (Exception e) {
      e.printStackTrace();
      return new UserResponse();
    }
  }
}