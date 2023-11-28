package propuestas;

import entidades.Comunidad;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

public class PropuestaDeFusion {

    @Getter @Setter
    private Comunidad unaComunidad;

    @Getter @Setter
    private Comunidad otraComunidad;

    @Getter @Setter
    private LocalDate fechaDePropuesta;

    @Getter @Setter
    private Boolean fueAceptada;

    public PropuestaDeFusion(Comunidad unaComunidad, Comunidad otraComunidad){
        this.setUnaComunidad(unaComunidad);
        this.setOtraComunidad(otraComunidad);
        this.fechaDePropuesta = LocalDate.now();
    }

    public PropuestaDeFusion() {
    }

}
