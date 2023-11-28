package entidades;

import lombok.Getter;

public class Servicio {

  @Getter
  private int id;

  @Getter
  private String nombre;

  public Servicio(String nombre) {
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
    Servicio servicio = (Servicio) obj;
    return this.id == servicio.getId();
  }

  public Servicio() {
  }

}
