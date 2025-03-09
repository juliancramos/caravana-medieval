package web.app.caravanamedieval.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.mapper.ProductoMapper;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.service.*;


import web.app.caravanamedieval.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
public class DataInitializer implements CommandLineRunner {


    @Autowired
    private CaravanaServiceJ caravanaService;
    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ConexionCiudadRepository conexionCiudadRepository;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ServicioRepository servicioRepository;  

    @Override
    public void run(String... args) throws Exception {
 
//        createJugadores();
//        createCiudades();
//        createProductos();
//        createServicios();
    }

    private void createJugadores() {
        String[] roles = {"Comerciante", "Caravanero", "Administrador"};
        for (int i = 0; i < 10; i++) {
            String username = "Jugador" + (i + 1);
            String password = "password" + (i + 1);
            String rol = roles[new Random().nextInt(roles.length)];
            Jugador jugador = new Jugador(null, username, password, rol, null);
            jugadorRepository.save(jugador);
            System.out.println("Jugador creado: " + username);
        }
    }

    private void createCiudades() {
        List<Ciudad> ciudades = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            String nombreCiudad = "Ciudad" + (i + 1);
            int impuestoEntrada = new Random().nextInt(100) + 1;
            Ciudad ciudad = new Ciudad(nombreCiudad, impuestoEntrada);
            ciudadRepository.save(ciudad);
            ciudades.add(ciudad);
            System.out.println("Ciudad creada: " + nombreCiudad);
        }


        createRutasAleatorias(ciudades);
    }

    private void createRutasAleatorias(List<Ciudad> ciudades) {
//        Random random = new Random();
//
//        for (int i = 0; i < 100; i++) {
//
//            Ciudad ciudadOrigen = ciudades.get(random.nextInt(ciudades.size()));
//            Ciudad ciudadDestino = ciudades.get(random.nextInt(ciudades.size()));
//
//            while (ciudadOrigen.equals(ciudadDestino)) {
//                ciudadDestino = ciudades.get(random.nextInt(ciudades.size()));
//            }
//
//            // Generar un tipo de ruta aleatorio
//            String[] tiposDeRuta = {"segura", "insegura"};
//            String tipo = tiposDeRuta[random.nextInt(tiposDeRuta.length)];
//
//            // Declarar la variable `dano` fuera del bloque if-else
//            Integer dano = 0;
//
//            // Asignar un valor de daño si la ruta es "insegura"
//            if ("insegura".equals(tipo)) {
//                dano = random.nextInt(100);  // Daño entre 0 y 99
//            }
//
//            String causaDano = (dano > 0) ? "Desastre natural" : null;
//
//            // Crear la ruta con la información generada
//            Ruta ruta = new Ruta(tipo, ciudadOrigen, ciudadDestino, dano, causaDano);
//
//            // Guardar la ruta en la base de datos
//            rutaRepository.save(ruta);
//            System.out.println("Ruta creada entre " + ciudadOrigen.getNombre() + " y " + ciudadDestino.getNombre());
//        }
    }

    private void createProductos() {
        String[] nombresProducto = {"Espada", "Escudo", "Poción", "Armadura", "Anillo", "Arco", "Daga", "Báculo", "Casco", "Botas"};
        String[] descripcionesProducto = {
                "Arma afilada para combate", "Escudo protector", "Poción curativa", "Armadura de hierro", "Anillo mágico",
                "Arco de madera", "Daga afilada", "Báculo con poder", "Casco resistente", "Botas veloces"
        };
        for (int i = 0; i < 50; i++) {
            String nombre = nombresProducto[new Random().nextInt(nombresProducto.length)] + " " + (i + 1);
            String descripcion = descripcionesProducto[new Random().nextInt(descripcionesProducto.length)];
            Float peso = (float) (new Random().nextInt(100) + 1); // Peso entre 1 y 100
            ProductoDTO productoDTO = new ProductoDTO(nombre, descripcion, peso);
            Producto producto = ProductoMapper.INSTANCE.toEntity(productoDTO);
            productoRepository.save(producto);
            System.out.println("Producto creado: " + nombre);
        }
    }

//    private void createServicios() {
//
//    Servicio servicio1 = new Servicio(null, "Reparar", "Por una suma de dinero, la caravana puede recuperar todos o parte de sus puntos de vida", 0.10f, 0.50f);
//    Servicio servicio2 = new Servicio(null, "Mejorar capacidad", "Por una suma de dinero, la caravana puede ampliar su capacidad maxima de carga. A través de este servicio se puede aumentar hasta un tope de 400% por sobre la capacidad maxima original de la caravana", 0.10f, 4f);
//    Servicio servicio3 = new Servicio(null, "Mejorar velocidad", "Por una suma de dinero, la velocidad maxima de la caravana puede ser aumentada, hasta un tope maximo de 50%, por sobre la velocidad original de la caravana", 0.10f, 0.5f);
//    Servicio servicio4 = new Servicio(null, "Guardias", "Por una suma de dinero, la caravana adquiere, permanentemente, mejor protección para viajar por rutas inseguras. La cantidad de daño que recibe durante un viaje inseguro se reduce un 25%. Este servicio no es acumulable (solo se puede comprar una vez)", 0.10f, 0.5f);
//
//    servicioRepository.save(servicio1);
//    System.out.println("Servicio guardado: " + servicio1.getNombre());
//
//
//
//    servicioRepository.save(servicio2);
//    System.out.println("Servicio guardado: " + servicio2.getNombre());
//
//    servicioRepository.save(servicio3);
//    System.out.println("Servicio guardado: " + servicio3.getNombre());
//
//    servicioRepository.save(servicio4);
//    System.out.println("Servicio guardado: " + servicio4.getNombre());
//
//        System.out.println("Servicios creados.");
//    }
}