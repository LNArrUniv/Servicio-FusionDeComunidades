package entidades;

import lombok.Getter;
import lombok.Setter;

public class PrestacionDeServicio {

  @Getter
  private int id;

  @Getter
  private Servicio servicio;

  @Getter
  private Establecimiento establecimiento;

  public PrestacionDeServicio(Servicio servicio, Establecimiento establecimiento){
    this.servicio = servicio;
    this.establecimiento = establecimiento;
  }

  private PrestacionDeServicio() {
  }

}
