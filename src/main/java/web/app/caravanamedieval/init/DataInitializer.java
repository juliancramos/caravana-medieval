package web.app.caravanamedieval.init;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import web.app.caravanamedieval.dto.*;
import web.app.caravanamedieval.mapper.*;
import web.app.caravanamedieval.model.*;
import web.app.caravanamedieval.service.*;


import web.app.caravanamedieval.repository.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

@Component
public class DataInitializer implements CommandLineRunner {


    @Autowired
    private CaravanaServiceJ caravanaService;
    @Autowired
    private JugadorRepository jugadorRepository;

    @Autowired
    private CiudadRepository ciudadRepository;

    @Autowired
    private CiudadService ciudadService;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private MapaService mapaService;

    @Autowired
    private CiudadMapper ciudadMapper;

    @Autowired
    private RutaRepository rutaRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private MapaRepository mapaRepository;

    @Autowired
    private MapaServiceImpl mapaServiceImpl;

    @Autowired
    private CaravanaRepository caravanaRepository;

    @Autowired
    private PartidaRepository partidaRepository;

    @Override
    public void run(String... args) throws Exception {
//        createJugadores();
//        createCaravanas();
//        createMapas();
//        createCiudades();
//        createPartidas();
//        createServicios();
//        createProductos();

    }

    private void createMapas() {
        String[] nombres = {"Ruta del Norte", "Camino del Desierto", "Senda de los Valles"};
        String[] descripciones = {
                "Una ruta extensa que conecta el norte con el centro.",
                "Un camino peligroso que cruza el desierto árido.",
                "Una senda verde que atraviesa los valles montañosos."
        };

        for (int i = 0; i < 3; i++) {
            MapaDTO mapaDTO = new MapaDTO();
            mapaDTO.setNombre(nombres[i]);
            mapaDTO.setDescripcion(descripciones[i]);
            mapaDTO.setCiudadesIds(new ArrayList<>());
            mapaDTO.setPartidasIds(new ArrayList<>());

            Mapa mapa = MapaMapper.INSTANCE.toEntity(mapaDTO);
            mapaRepository.save(mapa);

            System.out.println("Mapa creado: " + mapa.getNombre());
        }
    }
    private void createCaravanas() {
        List<Producto> productos = productoRepository.findAll();
        Random random = new Random();

        for (int i = 1; i <= 30; i++) {
            String nombre = "Caravana" + i;
            double velocidad = 20.0 + random.nextDouble() * 30.0;  // Velocidad entre 20 y 50
            double capacidadMaxima = 500.0 + random.nextDouble() * 1000.0;  // Capacidad entre 500 y 1500
            long dineroDisponible = random.nextInt(5000) + 1000;  // Dinero entre 1000 y 6000
            int puntosVida = random.nextInt(80) + 20;  // Puntos de vida entre 20 y 100

            List<Producto> productosAleatorios = productos.stream()
                    .filter(p -> random.nextBoolean())  // Incluye aleatoriamente algunos productos
                    .collect(Collectors.toList());

            Caravana caravana = new Caravana();
            caravana.setNombre(nombre);
            caravana.setVelocidad(velocidad);
            caravana.setCapacidadMaxima(capacidadMaxima);
            caravana.setDineroDisponible(dineroDisponible);
            caravana.setPuntosVida(puntosVida);
            caravana.setProductos(productosAleatorios);

            caravanaRepository.save(caravana);
            System.out.println("Caravana creada: " + nombre);
        }
    }

    private void createPartidas() {
        List<Mapa> mapas = mapaRepository.findAll();
        if (mapas.isEmpty()) {
            System.out.println("No hay mapas disponibles para asociar a las partidas.");
            return;
        }

        List<Caravana> caravanas = caravanaRepository.findAll();
        if (caravanas.isEmpty()) {
            System.out.println("No hay caravanas disponibles para asociar a las partidas.");
            return;
        }

        for (int i = 0; i < 3; i++) {
            Double tiempoTranscurrido = new Random().nextDouble() * 100.0;  // Tiempo transcurrido aleatorio
            Double tiempoLimite = tiempoTranscurrido + 50.0;  // Tiempo límite un poco mayor
            Long gananciaMinima = (long) (new Random().nextInt(1000) + 500);  // Ganancia mínima aleatoria

            Caravana caravana = caravanas.get(i % caravanas.size());
            Mapa mapa = mapas.get(i % mapas.size());

            Partida partida = new Partida();
            partida.setTiempoTranscurrido(tiempoTranscurrido);
            partida.setTiempoLimite(tiempoLimite);
            partida.setGananciaMinima(gananciaMinima);
            partida.setCaravana(caravana);
            partida.setMapa(mapa);

            partidaRepository.save(partida);
            System.out.println("Partida creada con ID: " + partida.getIdPartida() + " asociada al mapa: " + mapa.getNombre());
        }
    }



    private void createJugadores() {
        String[] roles = {"Comerciante", "Caravanero", "Administrador"};
        for (int i = 0; i < 10; i++) {
            String username = "Jugador" + (i + 1);
            String password = "password" + (i + 1);
            String rol = roles[new Random().nextInt(roles.length)];

            // Crear DTO
            JugadorDTO jugadorDTO = new JugadorDTO(username, password, rol);

            // Convertir DTO a entidad usando MapStruct
            Jugador jugador = JugadorMapper.INSTANCE.toEntity(jugadorDTO);

            // Guardar en la base de datos
            jugadorRepository.save(jugador);

            System.out.println("Jugador creado: " + username);
        }
    }

    private void createCiudades() {
        List<Ciudad> ciudades = new ArrayList<>();
        Random random = new Random();
        Long[] mapaIds = {1L, 2L, 3L};  // Suponiendo que ya hay tres mapas creados con estos IDs

        for (int i = 0; i < 100; i++) {
            String nombreCiudad = "Ciudad" + (i + 1);
            int impuestoEntrada = random.nextInt(100) + 1;
            Long mapaId = mapaIds[random.nextInt(mapaIds.length)];  // Selección aleatoria de un mapa

            try {
                // Obtener el mapa directamente
                Mapa mapa = mapaService.getMapa(mapaId);

                // Crear la ciudad
                CiudadDTO ciudadDTO = new CiudadDTO();
                ciudadDTO.setNombre(nombreCiudad);
                ciudadDTO.setImpuestoEntrada(impuestoEntrada);
                ciudadDTO.setMapaId(mapaId);

                // Convertir el DTO a la entidad y asignar el mapa
                Ciudad ciudad = ciudadMapper.toEntity(ciudadDTO);
                ciudad.setMapa(mapa);

                // Guardar la ciudad usando el servicio
                ciudad = ciudadService.crearCiudad(ciudad);
                ciudades.add(ciudad);

                System.out.println("Ciudad creada: " + nombreCiudad + " en el mapa ID: " + mapaId);
            } catch (Exception e) {
                System.err.println("Error al crear la ciudad: " + nombreCiudad + " - " + e.getMessage());
            }
        }

        createRutasAleatorias(ciudades);
    }


    private void createRutasAleatorias(List<Ciudad> ciudades) {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            Ciudad ciudadOrigen = ciudades.get(random.nextInt(ciudades.size()));
            Ciudad ciudadDestino = ciudades.get(random.nextInt(ciudades.size()));

            while (ciudadOrigen.equals(ciudadDestino)) {
                ciudadDestino = ciudades.get(random.nextInt(ciudades.size()));
            }

            // Generar un tipo de ruta aleatorio
            String[] tiposDeRuta = {"segura", "insegura"};
            String tipo = tiposDeRuta[random.nextInt(tiposDeRuta.length)];

            // Asignar un valor de daño si la ruta es "insegura"
            Integer dano = 0;
            String causaDano = null;
            if ("insegura".equals(tipo)) {
                dano = random.nextInt(100);  // Daño entre 0 y 99
                causaDano = "Desastre natural";
            }

            // Crear el DTO de la ruta
            RutaDTO rutaDTO = new RutaDTO();
            rutaDTO.setTipo(tipo);
            rutaDTO.setCiudadOrigenId(ciudadOrigen.getIdCiudad());
            rutaDTO.setCiudadDestinoId(ciudadDestino.getIdCiudad());
            rutaDTO.setDano(dano);
            rutaDTO.setCausaDano(causaDano);

            // Convertir el DTO a la entidad usando el mapper
            Ruta ruta = RutaMapper.INSTANCE.toEntity(rutaDTO, ciudadOrigen, ciudadDestino);

            // Guardar la ruta usando el repositorio
            rutaRepository.save(ruta);
            System.out.println("Ruta creada entre " + ciudadOrigen.getNombre() + " y " + ciudadDestino.getNombre());
        }
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

    private void createServicios() {
        ServicioDTO servicio1 = new ServicioDTO(
                "Reparar",
                "Por una suma de dinero, la caravana puede recuperar todos o parte de sus puntos de vida",
                0.10f,
                0.50f
        );
        ServicioDTO servicio2 = new ServicioDTO(
                "Mejorar capacidad",
                "Por una suma de dinero, la caravana puede ampliar su capacidad máxima de carga. A través de este servicio se puede aumentar hasta un tope de 400% por sobre la capacidad máxima original de la caravana",
                0.10f,
                4f
        );
        ServicioDTO servicio3 = new ServicioDTO(
                "Mejorar velocidad",
                "Por una suma de dinero, la velocidad máxima de la caravana puede ser aumentada, hasta un tope máximo de 50%, por sobre la velocidad original de la caravana",
                0.10f,
                0.5f
        );
        ServicioDTO servicio4 = new ServicioDTO(
                "Guardias",
                "Por una suma de dinero, la caravana adquiere, permanentemente, mejor protección para viajar por rutas inseguras. La cantidad de daño que recibe durante un viaje inseguro se reduce un 25%. Este servicio no es acumulable (solo se puede comprar una vez)",
                0.10f,
                0.5f
        );

        servicioRepository.save(ServicioMapper.INSTANCE.toEntity(servicio1));
        System.out.println("Servicio guardado: " + servicio1.getNombre());

        servicioRepository.save(ServicioMapper.INSTANCE.toEntity(servicio2));
        System.out.println("Servicio guardado: " + servicio2.getNombre());

        servicioRepository.save(ServicioMapper.INSTANCE.toEntity(servicio3));
        System.out.println("Servicio guardado: " + servicio3.getNombre());

        servicioRepository.save(ServicioMapper.INSTANCE.toEntity(servicio4));
        System.out.println("Servicio guardado: " + servicio4.getNombre());

        System.out.println("Servicios creados.");
    }


}