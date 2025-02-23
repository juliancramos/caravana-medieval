package web.app.caravanamedieval.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "ciudad")
public class Ciudad {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_ciudad")
    private Long idCiudad;

    @Column(name = "nombre", nullable = false, length = 30)
    private String nombre;

    @Column(name = "impuesto_entrada", nullable = false)
    private int impuestoEntrada;

    @ManyToMany(mappedBy = "ciudades")
    @JsonIgnore //Evita recursiones infinitas al escribir una ciudad
    private List<Mapa> mapas;

    //Representa las conexiones de entrada y salida EN LAS CONEXIONES
    @OneToMany(mappedBy = "ciudadOrigen", cascade = CascadeType.ALL)
    @JsonIgnore //Evita recursiones infinitas al escribir una ciudad
    private List<ConexionCiudad> conexionesSalida = new ArrayList<>();

    @OneToMany(mappedBy = "ciudadDestino", cascade = CascadeType.ALL)
    @JsonIgnore //Evita recursiones infinitas al escribir una ciudad
    private List<ConexionCiudad> conexionesEntrada = new ArrayList<>();

    //Representa la ciudad de origen y de destino EN LAS RUTAS
    @OneToMany(mappedBy = "ciudadOrigen", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ruta> rutasOrigen = new ArrayList<>();

    @OneToMany(mappedBy = "ciudadDestino", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore
    private List<Ruta> rutasSalida = new ArrayList<>();

    public Ciudad(){
        this.mapas = new ArrayList<>();
    }


    public Ciudad(String nombre, int impuestoEntrada) {
        this.nombre = nombre;
        this.impuestoEntrada = impuestoEntrada;
    }

    public long getIdCiudad() {
        return idCiudad;
    }

    public void setIdCiudad(long idCiudad) {
        this.idCiudad = idCiudad;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getImpuestoEntrada() {
        return impuestoEntrada;
    }

    public void setImpuestoEntrada(int impuestoEntrada) {
        this.impuestoEntrada = impuestoEntrada;
    }

    public List<Mapa> getMapas() {
        return mapas;
    }

    public List<ConexionCiudad> getConexionesSalida() {
        return conexionesSalida;
    }

    public void setConexionesSalida(List<ConexionCiudad> conexionesSalida) {
        this.conexionesSalida = conexionesSalida;
    }

    public List<ConexionCiudad> getConexionesEntrada() {
        return conexionesEntrada;
    }

    public void setConexionesEntrada(List<ConexionCiudad> conexionesEntrada) {
        this.conexionesEntrada = conexionesEntrada;
    }

    public List<Ruta> getRutasOrigen() {
        return rutasOrigen;
    }

    public void setRutasOrigen(List<Ruta> rutasOrigen) {
        this.rutasOrigen = rutasOrigen;
    }

    public List<Ruta> getRutasSalida() {
        return rutasSalida;
    }

    public void setRutasSalida(List<Ruta> rutasSalida) {
        this.rutasSalida = rutasSalida;
    }
}
