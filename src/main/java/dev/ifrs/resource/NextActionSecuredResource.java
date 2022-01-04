package dev.ifrs.resource;

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
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.SecurityContext;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.eclipse.microprofile.jwt.Claims;
import org.eclipse.microprofile.jwt.JsonWebToken;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import dev.ifrs.dao.UserDaoImpl;
import dev.ifrs.model.NextAction;
import io.smallrye.jwt.build.Jwt;

@Path("/secured/")
public class NextActionSecuredResource {

  @ConfigProperty(name = "mp.jwt.verify.issuer")
  public String issuer;

  @Inject
  @RestClient
  NextActionClient service;

  @Inject
  UserDaoImpl dao;

  @Inject
  JsonWebToken jwt;

  @GET
  @Path("list")
  @RolesAllowed({ "User" })
  @Produces(MediaType.APPLICATION_JSON)
  public List<NextAction> list() {
    return service.list(jwt.getClaim(Claims.given_name.toString()));
  }

  @POST
  @Path("add/{type}/{title}")
  @RolesAllowed({ "User" })
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response add(@PathParam("type") final int type,
      @PathParam("title") final String title) {
    return service.add(jwt.getClaim(Claims.given_name.toString()), type, title);
  }

  @POST
  @Path("complete/{taskId}")
  @RolesAllowed({ "User" })
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response complete(@PathParam("taskId") final String taskId) {
    return service.complete(jwt.getClaim(Claims.given_name.toString()), taskId);
  }

  @POST
  @Path("rename/{taskId}/{newTitle}")
  @RolesAllowed({ "User" })
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response rename(@PathParam("taskId") final String taskId,
      @PathParam("newTitle") final String newTitle) {
    return service.rename(jwt.getClaim(Claims.given_name.toString()), taskId, newTitle);
  }

  @POST
  @Path("context/{taskId}/{newContext}")
  @RolesAllowed({ "User" })
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response context(@PathParam("taskId") final String taskId,
      @PathParam("newContext") final String newContext) {
    return service.context(jwt.getClaim(Claims.given_name.toString()), taskId, newContext);
  }

  @POST
  @Path("delete/{taskId}")
  @RolesAllowed({ "User" })
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  public Response delete(@PathParam("taskId") final String taskId) {
    return service.delete(jwt.getClaim(Claims.given_name.toString()), taskId);
  }

  @GET
  @Path("/login/{email}/{password}")
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON)
  public Response generate(@Context SecurityContext ctx,
      @PathParam("email") final String email,
      @PathParam("password") final String password) {
    try {
      final String userId = dao.authUser(email, password);
      return Response.ok(Jwt.issuer(issuer)
          .upn(email)
          .groups(new HashSet<>(Collections.singletonList("User")))
          .claim(Claims.given_name, userId)
          .sign()).build();
    } catch (Exception e) {
      e.printStackTrace();
      return Response.status(Status.UNAUTHORIZED).build();
    }
  }

  @POST
  @Path("/register/{email}/{password}")
  @PermitAll
  @Produces(MediaType.APPLICATION_JSON)
  public Response register(@Context SecurityContext ctx,
      @PathParam("email") final String email,
      @PathParam("password") final String password) {
    try {
      final String userId = dao.register(email, password);
      return Response.ok(Jwt.issuer(issuer)
          .upn(email)
          .groups(new HashSet<>(Collections.singletonList("User")))
          .claim(Claims.given_name, userId)
          .sign()).build();
    } catch (Exception e) {
      return Response.serverError().entity("Registration failed").build();
    }
  }
}