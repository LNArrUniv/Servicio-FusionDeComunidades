package controladores;

import static java.lang.Thread.sleep;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import entidades.Comunidad;
import io.javalin.http.Context;
import io.javalin.http.Handler;
import io.javalin.http.HttpStatus;
import propuestas.GeneradorDePropuestasDeFusion;
import propuestas.PropuestaDeFusion;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;

public class FusionController implements Handler {

    private final GeneradorDePropuestasDeFusion generador;

    public FusionController(GeneradorDePropuestasDeFusion generador) {
        this.generador = generador;
    }

    @Override
    public void handle(Context ctx) throws Exception {
        String path = ctx.path();

        if (path.equals("/posiblesfusiones")) {
            handleGenerarPosiblesFusiones(ctx);
        } else if (path.equals("/fusionarcomunidades")) {
            handleFusionarComunidades(ctx);
        } else {
            ctx.status(HttpStatus.NOT_FOUND);
        }
    }

    private void handleGenerarPosiblesFusiones(Context ctx) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        String comunidadesEndpoint = "https://tpa-dds.onrender.com/comunidades/obtenerTodas";
        sleep(500);
        try {
            // Realiza una solicitud HTTP GET para obtener las comunidades desde el sistema
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(comunidadesEndpoint))
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpStatus.OK.getCode()) {
                String comunidadesJson = response.body();
                List<Comunidad> comunidades = objectMapper.readValue(comunidadesJson, new TypeReference<List<Comunidad>>() {});

                List<PropuestaDeFusion> propuestas = new ArrayList<>(this.generador.generarPropuestasDeFusionPara(comunidades));

                System.out.println("Devolviendo respuesta de la petición GenerarPosiblesFusionesController");

                if (propuestas.isEmpty()) {
                    ctx.status(HttpStatus.BAD_REQUEST);
                    ctx.result("No hay comunidades que cumplan con las condiciones de fusión");
                } else {
                    ctx.status(HttpStatus.OK);
                    ctx.json(propuestas);
                }
            } else {
                // Maneja el caso de error de la solicitud HTTP
                ctx.status(response.statusCode());
                ctx.result("Error al obtener las comunidades desde el sistema de comunidades");
            }
        } catch (Exception e) {
            // Maneja excepciones en caso de problemas de conexión
            e.printStackTrace();
            ctx.status(HttpStatus.INTERNAL_SERVER_ERROR);
            ctx.result("Error interno al procesar la solicitud");
        }
    }

    private void handleFusionarComunidades(Context ctx) throws Exception {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());

        PropuestaDeFusion propuestaDeFusion = objectMapper.readValue(ctx.body(), PropuestaDeFusion.class);

        System.out.println("Devolviendo respuesta de la peticion FusionarComunidadesController");

        if(!this.generador.getComparador().cumpleTodasLasCondiciones(propuestaDeFusion.getUnaComunidad(), propuestaDeFusion.getOtraComunidad(), this.generador.getPorcentajeEstablecimientosNecesario(), this.generador.getPorcentajeServiciosNecesario())){
            ctx.status(400);
            ctx.result("Estas comunidades no cumplen con las condiciones de fusión");
        }else{

            Comunidad comunidadFusionada;

            comunidadFusionada = this.generador.fusionar(propuestaDeFusion.getUnaComunidad(),propuestaDeFusion.getOtraComunidad());

            ctx.status(HttpStatus.OK);
            ctx.json(comunidadFusionada);
        }
    }

}