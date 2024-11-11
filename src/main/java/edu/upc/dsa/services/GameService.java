package edu.upc.dsa.services;


import edu.upc.dsa.CampusManager;
import edu.upc.dsa.CampusManagerImpl;
import edu.upc.dsa.models.Usuari;
import edu.upc.dsa.models.PointOfInterest;
import edu.upc.dsa.models.ElementType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/game", description = "Endpoint to Game Service")
@Path("/game")
public class GameService {

    private CampusManager manager;

    public GameService() {
        this.manager = CampusManagerImpl.getInstance();
    }

    @POST
    @ApiOperation(value = "create a new user", notes = "a")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=Usuari.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/adduser")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewUser(Usuari user) {

        if (user.getSurname()==null || user.getEmail()==null || user.getName()==null)  return Response.status(500).entity(user).build();
        this.manager.addUser(user);
        return Response.status(201).entity(user).build();
    }

    @POST
    @ApiOperation(value = "add a new point of interest", notes = "a")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response=PointOfInterest.class),
            @ApiResponse(code = 500, message = "Validation Error")

    })

    @Path("/addpointofinterest")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addNewPointOfInterest(PointOfInterest point) {

        if (point.getType()==null)  return Response.status(500).entity(point).build();
        this.manager.addPointOfInterest(point);
        return Response.status(201).entity(point).build();
    }

    @GET
    @ApiOperation(value = "get all users alphabetically", notes = "a")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuari.class, responseContainer="List"),
    })

    @Path("/getusersalphabetically")
    @Produces(MediaType.APPLICATION_JSON)
    public Response returnAlphabetically() {

        List<Usuari> users = this.manager.listUsersAlphabetically();
        return Response.status(200).entity(users).build()  ;
    }

    @GET
    @ApiOperation(value = "get a user by id", notes = "aaa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuari.class),
    })

    @Path("/user/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserOrders(@PathParam("user_id") String user_id) {

        Usuari usuari = manager.getUsuari(user_id);
        return Response.status(200).entity(usuari).build()  ;
    }

    @GET
    @ApiOperation(value = "get user's visited locations", notes = "aaa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = PointOfInterest.class, responseContainer ="List"),
            @ApiResponse(code = 404, message = "User not found")

    })
    @Path("/pointsofinterest/{user_id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserVisitedPOIs(@PathParam("user_id") String user_id) {
        if (manager.getUsuari(user_id)==null)  return Response.status(404).entity(PointOfInterest.class).build();
        List<PointOfInterest> visited = this.manager.getUserVisitedPoints(user_id);
        return Response.status(200).entity(visited).build() ;
    }

    @GET
    @ApiOperation(value = "get visitors of a location", notes = "aaa")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Successful", response = Usuari.class, responseContainer ="List"),
            @ApiResponse(code = 405, message = "Point of interest not found")


    })
    @Path("/pointsofinterest/visitors/{x}/{y}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPOIVisitedByUser(@PathParam("x") int x, @PathParam("y") int y) {
        List<Usuari> visitors = this.manager.getUsersByPointOfInterest(x, y);
        if (manager.getPointOfInterest(x, y)==null) return Response.status(405).entity(visitors).build();
        return Response.status(200).entity(visitors).build() ;
    }

    @GET
    @ApiOperation(value = "get all the locations of a certain type", notes = "aaa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = Usuari.class),
            @ApiResponse(code = 404, message = "Type not found")

    })

    @Path("/pointsofinterest/type/{type}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getPOIByType(@PathParam("type") ElementType type) {
        if (manager.getPointsOfInterestByType(type)==null)  return Response.status(404).entity(PointOfInterest.class).build();
        List<PointOfInterest> points = manager.getPointsOfInterestByType(type);
        return Response.status(201).entity(points).build()  ;
    }

    @GET
    @ApiOperation(value = "get visitors of a location", notes = "aaa")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful"),
            @ApiResponse(code = 404, message = "User not found"),
            @ApiResponse(code = 405, message = "Point of interest not found")

    })
    @Path("/addvisit/{user_id}/{x}/{y}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response AddVisit(@PathParam("user_id") String user_id, @PathParam("x") int x, @PathParam("y") int y) {
        if (manager.getUsuari(user_id)==null)  return Response.status(404).build();
        if (manager.getPointOfInterest(x, y)==null) return Response.status(405).build();
        manager.registerUserVisit(user_id, x, y);
        return Response.status(201).build()  ;
    }


}