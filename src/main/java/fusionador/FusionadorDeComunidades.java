package fusionador;

import entidades.Comunidad;
import entidades.PrestacionDeServicio;
import propuestas.ComparadorDeComunidades;

import java.util.*;

public class FusionadorDeComunidades {

    ComparadorDeComunidades comparador;

    public FusionadorDeComunidades(){
        this.comparador = new ComparadorDeComunidades();
    }

    public Comunidad fusionar(Comunidad unaComunidad, Comunidad otraComunidad, Double porcentajeEstablecimientosNecesario, Double porcentajeServiciosNecesario){
            String unNombre = unaComunidad.getNombre();
            String otroNombre = otraComunidad.getNombre();
            Double unGradoDeConfianza = unaComunidad.getGradoDeConfianza();
            Double otroGradoDeConfianza = otraComunidad.getGradoDeConfianza();
            List<PrestacionDeServicio> unasPrestaciones = unaComunidad.getServiciosParticularesObservados();
            List<PrestacionDeServicio> otrasPrestaciones = otraComunidad.getServiciosParticularesObservados();

            String nombreFusion = this.fusionarNombres(unNombre, otroNombre);
            Double gradoDeConfianzaFusion = this.fusionarGradoDeConfianza(unGradoDeConfianza, otroGradoDeConfianza);
            List<PrestacionDeServicio> serviciosParticularesObservados = this.fusionarServiciosParticularesObservados(unasPrestaciones, otrasPrestaciones);

            return new Comunidad(nombreFusion, gradoDeConfianzaFusion, serviciosParticularesObservados);
    }

    public String fusionarNombres(String unNombre, String otroNombre){
        return unNombre + " " + otroNombre;
    }

    public Double fusionarGradoDeConfianza(Double unGrado, Double otroGrado){
        return (unGrado + otroGrado) / 2;
    }


    public List<PrestacionDeServicio> fusionarServiciosParticularesObservados(List<PrestacionDeServicio> unasPrestaciones, List<PrestacionDeServicio> otrasPrestaciones){

        for(PrestacionDeServicio prestacion : otrasPrestaciones){
            if(unasPrestaciones.stream().noneMatch(prestacionDeServicio -> prestacionDeServicio.getId() == prestacion.getId())){
                unasPrestaciones.add(prestacion);
            }
        }

        return unasPrestaciones;
    }
}
