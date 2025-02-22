package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.app.caravanamedieval.dto.ConexionCiudadDTO;
import web.app.caravanamedieval.model.ConexionCiudad;
import web.app.caravanamedieval.service.ConexionCiudadService;

import java.util.List;

@RestController
@RequestMapping("/conexion")
public class CrearConexionController {

    @Autowired
    ConexionCiudadService conexionCiudadService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearConexion(@RequestBody ConexionCiudadDTO conexionCiudadDTO) {
        try {
            ConexionCiudad conexion = conexionCiudadService.crearConexion(conexionCiudadDTO);
            return ResponseEntity.ok(conexion);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la conexión: " + e.getMessage());
        }
    }

    @GetMapping("/desde/{ciudadId}")
    public ResponseEntity<List<ConexionCiudad>> obtenerConexionesDesde(@PathVariable Long ciudadId) {
        return ResponseEntity.ok(conexionCiudadService.obtenerConexionesDesdeCiudad(ciudadId));
    }

    @GetMapping("/hacia/{ciudadId}")
    public ResponseEntity<List<ConexionCiudad>> obtenerConexionesHacia(@PathVariable Long ciudadId) {
        return ResponseEntity.ok(conexionCiudadService.obtenerConexionesHaciaCiudad(ciudadId));
    }
}
