package web.app.caravanamedieval.model;

import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "caravana")
public class Caravana {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //Se usa identity ya que en Postgre está como "serial"
    @Column(name = "id_caravana")
    private Long idCaravana;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "velocidad", nullable = false)
    private double velocidad;

    @Column(name = "capacidad_maxima", nullable = false)
    private double capacidadMaxima;

    @Column(name = "dinero_disponible", nullable = false)
    private long dineroDisponible;

    @Column(name = "puntos_vida", nullable = false)
    private int puntosVida;

    //Constructor sin id para evitar problemas con generación automática
    public Caravana(String nombre, float velocidad, float capacidadMaxima, long dineroDisponible, int puntosVida) {
        this.nombre = nombre;
        this.velocidad = velocidad;
        this.capacidadMaxima = capacidadMaxima;
        this.dineroDisponible = dineroDisponible;
        this.puntosVida = puntosVida;
    }

 

    // Getters y Setters
    public Long getIdCaravana() {
        return idCaravana;
    }

    public void setIdCaravana(Long idCaravana) {
        this.idCaravana = idCaravana;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public double getVelocidad() {
        return velocidad;
    }

    public void setVelocidad(double velocidad) {
        this.velocidad = velocidad;
    }

    public double getCapacidadMaxima() {
        return capacidadMaxima;
    }

    public void setCapacidadMaxima(double capacidadMaxima) {
        this.capacidadMaxima = capacidadMaxima;
    }

    public long getDineroDisponible() {
        return dineroDisponible;
    }

    public void setDineroDisponible(long dineroDisponible) {
        this.dineroDisponible = dineroDisponible;
    }

    public int getPuntosVida() {
        return puntosVida;
    }

    public void setPuntosVida(int puntosVida) {
        this.puntosVida = puntosVida;
    }
}
