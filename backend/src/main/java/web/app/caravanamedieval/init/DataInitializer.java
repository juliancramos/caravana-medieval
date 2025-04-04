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
    private PlayerRepository playerRepository;

    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private CityService cityService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MapService mapService;

    @Autowired
    private CityMapper cityMapper;

    @Autowired
    private RouteRepository routeRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private MapRepository mapRepository;

    @Autowired
    private MapServiceImpl mapServiceImpl;

    @Autowired
    private CaravanRepository caravanRepository;

    @Autowired
    private GameRepository gameRepository;

    @Override
    public void run(String... args) throws Exception {
//        createPlayers();
//        createCaravans();
//        createMaps();
//        createCities();
//        createGames();
//        createServices();
//        createProducts();
    }

    private void createMaps() {
        String[] names = {"Ruta del Norte", "Camino del Desierto", "Senda de los Valles"};
        String[] descriptions = {
                "Una ruta extensa que conecta el norte con el centro.",
                "Un camino peligroso que cruza el desierto árido.",
                "Una senda verde que atraviesa los valles montañosos."
        };

        for (int i = 0; i < 3; i++) {
            MapDTO mapDTO = new MapDTO();
            mapDTO.setName(names[i]);
            mapDTO.setDescription(descriptions[i]);
            mapDTO.setCities(new ArrayList<>());

            Map map = MapMapper.INSTANCE.toEntity(mapDTO);
            mapRepository.save(map);

            System.out.println("Mapa creado: " + map.getName());
        }
    }

    private void createCaravans() {
        List<Product> products = productRepository.findAll();
        Random random = new Random();

        for (int i = 1; i <= 30; i++) {
            String name = "Caravana" + i;
            double speed = 20.0 + random.nextDouble() * 30.0;  // Velocidad entre 20 y 50
            double maxCapacity = 500.0 + random.nextDouble() * 1000.0;  // Capacidad entre 500 y 1500
            long availableMoney = random.nextInt(5000) + 1000;  // Dinero entre 1000 y 6000
            int lifePoints = random.nextInt(80) + 20;  // Puntos de vida entre 20 y 100

            List<Product> randomProducts = products.stream()
                    .filter(p -> random.nextBoolean())  // Incluye aleatoriamente algunos productos
                    .collect(Collectors.toList());

            Caravan caravan = new Caravan();
            caravan.setName(name);
            caravan.setSpeed(speed);
            caravan.setMaxCapacity(maxCapacity);
            caravan.setAvailableMoney(availableMoney);
            caravan.setLifePoints(lifePoints);
            caravan.setProducts(randomProducts);

            caravanRepository.save(caravan);
            System.out.println("Caravana creada: " + name);
        }
    }

    private void createGames() {
        List<Map> maps = mapRepository.findAll();
        if (maps.isEmpty()) {
            System.out.println("No hay mapas disponibles para asociar a las partidas.");
            return;
        }

        List<Caravan> caravans = caravanRepository.findAll();
        if (caravans.isEmpty()) {
            System.out.println("No hay caravanas disponibles para asociar a las partidas.");
            return;
        }

        for (int i = 0; i < 3; i++) {
            Double elapsedTime = new Random().nextDouble() * 100.0;  // Tiempo transcurrido aleatorio
            Double timeLimit = elapsedTime + 50.0;  // Tiempo límite un poco mayor
            Long minimumGain = (long) (new Random().nextInt(1000) + 500);  // Ganancia mínima aleatoria

            Caravan caravan = caravans.get(i % caravans.size());
            Map map = maps.get(i % maps.size());

            Game game = new Game();
            game.setElapsedTime(elapsedTime);
            game.setTimeLimit(timeLimit);
            game.setMinProfit(minimumGain);
            game.setCaravan(caravan);
            game.setMap(map);

            gameRepository.save(game);
            System.out.println("Partida creada con ID: " + game.getIdGame() + " asociada al mapa: " + map.getName());
        }
    }

    private void createPlayers() {
        String[] roles = {"Comerciante", "Caravanero", "Administrador"};
        for (int i = 0; i < 10; i++) {
            String username = "Jugador" + (i + 1);
            String password = "password" + (i + 1);
            String role = roles[new Random().nextInt(roles.length)];

            // Create DTO
            PlayerDTO playerDTO = new PlayerDTO(username, password, role);

            // Convert DTO to entity using MapStruct
            Player player = PlayerMapper.INSTANCE.toEntity(playerDTO);

            // Save to database
            playerRepository.save(player);

            System.out.println("Jugador creado: " + username);
        }
    }

    private void createCities() {
        List<City> cities = new ArrayList<>();
        Random random = new Random();
        Long[] mapIds = {1L, 2L, 3L};  // Suponiendo que ya hay tres mapas creados con estos IDs

        for (int i = 0; i < 100; i++) {
            String cityName = "Ciudad" + (i + 1);
            int entryTax = random.nextInt(100) + 1;
            Long mapId = mapIds[random.nextInt(mapIds.length)];  // Selección aleatoria de un mapa

            try {
                // Get the map directly
                Map map = mapService.getMap(mapId);

                // Create the city
                CityDTO cityDTO = new CityDTO();
                cityDTO.setName(cityName);
                cityDTO.setEntryTax(entryTax);
                cityDTO.setMapId(mapId);



                // Save the city using the service
                City city = cityService.createCity(cityDTO);
                cities.add(city);
                System.out.println("Ciudad creada: " + cityName + " en el mapa ID: " + mapId);
            } catch (Exception e) {
                System.err.println("Error al crear la ciudad: " + cityName + " - " + e.getMessage());
            }
        }

        createRandomRoutes(cities);
    }

    private void createRandomRoutes(List<City> cities) {
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            City originCity = cities.get(random.nextInt(cities.size()));
            City destinationCity = cities.get(random.nextInt(cities.size()));

            while (originCity.equals(destinationCity)) {
                destinationCity = cities.get(random.nextInt(cities.size()));
            }

            // Generar un tipo de ruta aleatorio
            String[] routeTypes = {"segura", "insegura"};
            String type = routeTypes[random.nextInt(routeTypes.length)];

            // Asignar valores de daño si la ruta es "insegura"
            Float damage = null;
            String damageCause = null;
            if ("insegura".equals(type)) {
                damage = random.nextFloat() * 100; // Daño entre 0 y 100
                damageCause = "Desastre natural";
            }

            // Generar tiempo de trayecto aleatorio
            int travelTime = random.nextInt(50) + 10; // Entre 10 y 60 minutos

            // Crear la ruta
            Route route = new Route();
            route.setType(type);
            route.setOriginCity(originCity);
            route.setDestinationCity(destinationCity);
            route.setDamage(damage);
            route.setDamageCause(damageCause);
            route.setTravelTime(travelTime);

            // Guardar la ruta en el repositorio
            routeRepository.save(route);
            System.out.println("Ruta creada entre " + originCity.getName() + " y " + destinationCity.getName());
        }
    }


    private void createProducts() {
        String[] productNames = {"Espada", "Escudo", "Poción", "Armadura", "Anillo", "Arco", "Daga", "Báculo", "Casco", "Botas"};
        String[] productDescriptions = {
                "Arma afilada para combate", "Escudo protector", "Poción curativa", "Armadura de hierro", "Anillo mágico",
                "Arco de madera", "Daga afilada", "Báculo con poder", "Casco resistente", "Botas veloces"
        };
        for (int i = 0; i < 50; i++) {
            String name = productNames[new Random().nextInt(productNames.length)] + " " + (i + 1);
            String description = productDescriptions[new Random().nextInt(productDescriptions.length)];
            Float weight = (float) (new Random().nextInt(100) + 1); // Peso entre 1 y 100
            ProductDTO productDTO = new ProductDTO(name, description, weight);
            Product product = ProductMapper.INSTANCE.toEntity(productDTO);
            productRepository.save(product);
            System.out.println("Producto creado: " + name);
        }
    }

    private void createServices() {
        ServicesDTO service1 = new ServicesDTO(
                "Reparar",
                "Por una suma de dinero, la caravana puede recuperar todos o parte de sus puntos de vida",
                0.10f,
                0.50f
        );
        ServicesDTO service2 = new ServicesDTO(
                "Mejorar capacidad",
                "Por una suma de dinero, la caravana puede ampliar su capacidad máxima de carga. A través de este servicio se puede aumentar hasta un tope de 400% por sobre la capacidad máxima original de la caravana",
                0.10f,
                4f
        );
        ServicesDTO service3 = new ServicesDTO(
                "Mejorar velocidad",
                "Por una suma de dinero, la velocidad máxima de la caravana puede ser aumentada, hasta un tope máximo de 50%, por sobre la velocidad original de la caravana",
                0.10f,
                0.5f
        );
        ServicesDTO service4 = new ServicesDTO(
                "Guardias",
                "Por una suma de dinero, la caravana adquiere, permanentemente, mejor protección para viajar por rutas inseguras. La cantidad de daño que recibe durante un viaje inseguro se reduce un 25%. Este servicio no es acumulable (solo se puede comprar una vez)",
                0.10f,
                0.5f
        );

        servicesRepository.save(ServicesMapper.INSTANCE.toEntity(service1));
        System.out.println("Servicio guardado: " + service1.getName());

        servicesRepository.save(ServicesMapper.INSTANCE.toEntity(service2));
        System.out.println("Servicio guardado: " + service2.getName());

        servicesRepository.save(ServicesMapper.INSTANCE.toEntity(service3));
        System.out.println("Servicio guardado: " + service3.getName());

        servicesRepository.save(ServicesMapper.INSTANCE.toEntity(service4));
        System.out.println("Servicio guardado: " + service4.getName());

        System.out.println("Servicios creados.");
    }
}
