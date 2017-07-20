package br.com.meuposto.servicos;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import br.com.meuposto.bo.PostoBO;
import br.com.meuposto.entity.Posto;


@Path("/posto")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class postoWS {

	@GET
	@Path("/porDistancia")
	public Response obterPorDistancia(@QueryParam("lat") double latitude, @QueryParam("long") double longitude) {
		try {
			List<Posto> resultado = PostoBO.getInstance().obterPostoPorDistancia(latitude, longitude);
			GenericEntity<List<Posto>> entidade = new GenericEntity<List<Posto>>(resultado) {
			};
			return Response.status(Status.OK).entity(entidade).build();
		} catch (Exception e) {
			e.printStackTrace();
			return Response.status(Status.INTERNAL_SERVER_ERROR).entity("Falha ao buscar estabelecimentos").build();
		}
	}

}
