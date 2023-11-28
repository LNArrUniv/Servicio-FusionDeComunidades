package main;

import controladores.FusionController;
import io.javalin.Javalin;
import propuestas.GeneradorDePropuestasDeFusion;

public class API {
    public static void main(String[] args) {
        Integer port = Integer.parseInt(System.getProperty("port", "8081"));
        Javalin app = Javalin.create().start(port);

        GeneradorDePropuestasDeFusion generador = new GeneradorDePropuestasDeFusion(1d, 1d);
        FusionController fusionController = new FusionController(generador);

        app.get("/posiblesfusiones", fusionController);
        app.post("/fusionarcomunidades", fusionController);
    }
}