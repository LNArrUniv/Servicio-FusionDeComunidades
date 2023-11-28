package propuestas;

import entidades.Comunidad;
import fusionador.FusionadorDeComunidades;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class GeneradorDePropuestasDeFusion {

    @Getter @Setter
    private Double porcentajeEstablecimientosNecesario;
    @Getter @Setter
    private Double porcentajeServiciosNecesario;
    @Getter
    private ComparadorDeComunidades comparador;
    private FusionadorDeComunidades fusionador;

    public GeneradorDePropuestasDeFusion(Double porcentajeEstablecimientosNecesario, Double porcentajeServiciosNecesario) {
        this.setPorcentajeEstablecimientosNecesario(porcentajeEstablecimientosNecesario);
        this.setPorcentajeServiciosNecesario(porcentajeServiciosNecesario);
        this.comparador = new ComparadorDeComunidades();
        this.fusionador = new FusionadorDeComunidades();
    }

    public List<PropuestaDeFusion> generarPropuestasDeFusionPara(List<Comunidad> comunidades){
        List<PropuestaDeFusion> propuestasDeFusion = new ArrayList<>();

        if(comunidades.size() >= 2) {
            for (int i = 0; i < comunidades.size() - 1; i++) {
                Comunidad unaComunidad = comunidades.get(i);

                for (int j = i + 1; j < comunidades.size(); j++) {
                    Comunidad otraComunidad = comunidades.get(j);

                    if (comparador.cumpleTodasLasCondiciones(unaComunidad, otraComunidad, porcentajeEstablecimientosNecesario, porcentajeServiciosNecesario)) {
                        if (propuestasDeFusion.stream().noneMatch(propuestaExistente -> this.comunidadesEstanEnPropuesta(unaComunidad, otraComunidad, propuestaExistente))) {
                            propuestasDeFusion.add(new PropuestaDeFusion(unaComunidad, otraComunidad));
                        }
                    }
                }
            }
        }

        return propuestasDeFusion;
    }

    public Boolean comunidadesEstanEnPropuesta(Comunidad unaComunidad, Comunidad otraComunidad ,PropuestaDeFusion propuestaExistente){
        if (propuestaExistente == null) {
            return false;
        }
        return (unaComunidad.getId() == propuestaExistente.getUnaComunidad().getId())
                || (unaComunidad.getId() == propuestaExistente.getOtraComunidad().getId())
                || (otraComunidad.getId() == propuestaExistente.getUnaComunidad().getId())
                || (otraComunidad.getId() == propuestaExistente.getOtraComunidad().getId());
    }

    public Comunidad fusionar(Comunidad unaComunidad, Comunidad otraComunidad){
        return this.fusionador.fusionar(unaComunidad, otraComunidad, porcentajeEstablecimientosNecesario, porcentajeServiciosNecesario);
    }
}
