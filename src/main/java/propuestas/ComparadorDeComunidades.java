package propuestas;

import entidades.Comunidad;
import entidades.Establecimiento;
import entidades.Servicio;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ComparadorDeComunidades {

    public Boolean cumpleTodasLasCondiciones(Comunidad unaComunidad, Comunidad otraComunidad, Double porcentajeEstablecimientosNecesario, Double porcentajeServiciosNecesario){
        return this.cumpleCoincidenciasDeEstablecimientos(unaComunidad, otraComunidad, porcentajeEstablecimientosNecesario)
               && this.cumpleCoincidenciasDeServicios(unaComunidad, otraComunidad, porcentajeServiciosNecesario)
               && this.tienenMismoGradoDeConfianza(unaComunidad, otraComunidad);
    }

    public Boolean tienenMismoGradoDeConfianza(Comunidad unaComunidad, Comunidad otraComunidad){
        return unaComunidad.getGradoDeConfianza().equals(otraComunidad.getGradoDeConfianza());
    }

    public Boolean cumpleCoincidenciasDeServicios(Comunidad unaComunidad, Comunidad otraComunidad, Double porcentajeNecesario){
        List<Servicio> serviciosUnaComunidad = unaComunidad.obtenerServicios();
        List<Servicio> serviciosOtraComunidad = otraComunidad.obtenerServicios();
        int coincidencias = 0;

        if (serviciosUnaComunidad.isEmpty() || serviciosOtraComunidad.isEmpty()) {
            return false;
        } else {

            for(Servicio servicio : serviciosUnaComunidad){
                if(serviciosOtraComunidad.contains(servicio)){
                    coincidencias += 1;
                }
            }
        }

        double porcentajeDeCoincidencias = (double) coincidencias / Math.max(serviciosUnaComunidad.size(), serviciosOtraComunidad.size());

        return porcentajeDeCoincidencias >= porcentajeNecesario;
    }

    public Boolean cumpleCoincidenciasDeEstablecimientos(Comunidad unaComunidad, Comunidad otraComunidad, Double porcentajeNecesario){
        List<Establecimiento> establecimientosDeUnaComunidad = unaComunidad.obtenerEstablecimientos();
        List<Establecimiento> establecimientosDeOtraComunidad = otraComunidad.obtenerEstablecimientos();

        if(establecimientosDeUnaComunidad.isEmpty() || establecimientosDeOtraComunidad.isEmpty())
            return false;

        int coincidencias = 0;

        for(Establecimiento establecimiento: establecimientosDeUnaComunidad){
            if(establecimientosDeOtraComunidad.contains(establecimiento))
                coincidencias++;
        }

        double porcentajeDeCoindencias = (double) coincidencias/ Math.max(establecimientosDeUnaComunidad.size(), establecimientosDeOtraComunidad.size());

        return porcentajeDeCoindencias >= porcentajeNecesario;

    }


}
