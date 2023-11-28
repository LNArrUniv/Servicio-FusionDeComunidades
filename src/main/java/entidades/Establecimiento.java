package entidades;
import lombok.Getter;

import java.util.List;

public class Establecimiento {

    @Getter
    private int id;

    @Getter
    private String nombre;

    public Establecimiento(int id, String nombre, List<PrestacionDeServicio> serviciosPrestados) {
        this.id = id;
        this.nombre = nombre;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Establecimiento establecimiento = (Establecimiento) obj;
        return this.id == establecimiento.getId();
    }
    private Establecimiento() {
    }

}
