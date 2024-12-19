package edu.upc.project.services;

import edu.upc.project.GameManager;
import edu.upc.project.GameManagerImpl;
import edu.upc.project.models.ElementType;
import edu.upc.project.models.FAQ;
import edu.upc.project.models.Item;
import edu.upc.project.models.User;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Api(value = "/faq", description = "Endpoint to Store Service")
@Path("/faq")
public class FAQService {

    private GameManager gm;

    public FAQService() {
        this.gm = GameManagerImpl.getInstance();
        if (gm.sizeQuestionsFAQs() == 0) {
            this.gm.createFAQ("Esta es la pregunta", "Esto es la respuesta");
            this.gm.createFAQ("Esta es la pregunta 2", "Esto es la respuesta 2");
            this.gm.createFAQ("Si quieres añadir más preguntas, ve a FAQService en IntelliJ" +
                    " y ve a la función FAQService", "A que es fácil añadir más preguntas?");
        }
    }

    @GET
    @ApiOperation(value = "Get all questions")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Successful", response = FAQ.class, responseContainer = "List"),
            @ApiResponse(code = 404, message = "No questions found")
    })
    @Path("/")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getFAQS() {

        if (this.gm.sizeQuestionsFAQs() == 0)
            return Response.status(404).build();
        else {
            GenericEntity<List<FAQ>> entity = new GenericEntity<List<FAQ>>(this.gm.getFAQS()) {
            };
            return Response.status(201).entity(entity).build();
        }
    }
}
