package entidades;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

public class Comunidad {

  @Getter
  private int id;

  @Getter
  private String nombre;

  @Getter
  private List<PrestacionDeServicio> serviciosParticularesObservados;

  @Getter
  private Double gradoDeConfianza;

  public Comunidad(String nombre) {
    this.setNombre(nombre);
    this.serviciosParticularesObservados = new ArrayList<>();
  }

  public Comunidad(String nombreFusion, Double gradoDeConfianza, List<PrestacionDeServicio> serviciosParticularesObservados) {
    this.nombre = nombreFusion;
    this.gradoDeConfianza = gradoDeConfianza;
    this.serviciosParticularesObservados = serviciosParticularesObservados;
  }

  private Comunidad(){
  }

  public void setNombre(String nombre) {
    this.nombre = nombre;
  }

  public List<Servicio> obtenerServicios(){
    return serviciosParticularesObservados.stream()
            .map(servicioParticular -> servicioParticular.getServicio())
            .toList();
  }

  public List<Establecimiento> obtenerEstablecimientos(){
    return serviciosParticularesObservados.stream()
            .map(servicioParticular -> servicioParticular.getEstablecimiento())
            .toList();
  }
}
