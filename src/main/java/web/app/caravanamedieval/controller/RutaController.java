package web.app.caravanamedieval.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import web.app.caravanamedieval.dto.RutaDTO;
import web.app.caravanamedieval.model.Ruta;
import web.app.caravanamedieval.service.RutaService;

import java.util.List;

@RestController
@RequestMapping("/ruta")
public class RutaController {
    @Autowired
    private RutaService rutaService;

    @PostMapping("/crear")
    public ResponseEntity<?> crearRuta(@RequestBody RutaDTO rutaDTO) {
        try{
            Ruta ruta = rutaService.crearRuta(rutaDTO);
            return ResponseEntity.ok(ruta);
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error al crear la ruta: " + e.getMessage());
        }
    }

    @GetMapping("/desde/{ciudadId}")
    public ResponseEntity<List<Ruta>> obtenerConexionesDesde(@PathVariable Long ciudadId) {
        return ResponseEntity.ok(rutaService.getRutaDesdeCiudad(ciudadId));
    }

    @GetMapping("/hacia/{ciudadId}")
    public ResponseEntity<List<Ruta>> obtenerConexionesHacia(@PathVariable Long ciudadId) {
        return ResponseEntity.ok(rutaService.getRutaHaciaCiudad(ciudadId));
    }
}
