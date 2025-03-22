package web.app.caravanamedieval.service;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import web.app.caravanamedieval.dto.CiudadDTO;
import web.app.caravanamedieval.mapper.CiudadMapper;
import web.app.caravanamedieval.model.Ciudad;
import web.app.caravanamedieval.model.Mapa;
import web.app.caravanamedieval.repository.CiudadRepository;
import web.app.caravanamedieval.repository.MapaRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CiudadServiceImpl implements CiudadService {

    private final CiudadRepository ciudadRepository;
    private final MapaRepository mapaRepository;
    private final CiudadMapper ciudadMapper;

    @Autowired
    public CiudadServiceImpl(CiudadRepository ciudadRepository, MapaRepository mapaRepository,
                             CiudadMapper ciudadMapper) {
        this.ciudadRepository = ciudadRepository;
        this.mapaRepository = mapaRepository;
        this.ciudadMapper = ciudadMapper;
    }

    @Override
    @Transactional
    public Ciudad crearCiudad(CiudadDTO ciudadDTO) {
        Ciudad ciudad = ciudadMapper.toEntity(ciudadDTO);

        if (ciudadDTO.getMapaId() != null) {
            Mapa mapa = mapaRepository.findById(ciudadDTO.getMapaId())
                    .orElseThrow(() -> new EntityNotFoundException("Mapa con ID " + ciudadDTO.getMapaId() + " no encontrado"));
            ciudad.setMapa(mapa);
        }

        return ciudadRepository.save(ciudad);
    }


    @Override
    public List<CiudadDTO> listarTodas() {
        List<Ciudad> ciudades = ciudadRepository.findAll();
        return ciudades.stream()
                .map(ciudadMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public CiudadDTO obtenerCiudad(Long id) {
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad con ID " + id + " no encontrada"));
        return ciudadMapper.toDTO(ciudad);
    }

    @Override
    @Transactional
    public CiudadDTO actualizarCiudad(Long id, CiudadDTO actualizado) {
        Ciudad ciudad = ciudadRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Ciudad con ID " + id + " no encontrada"));

        if (actualizado.getNombre() != null) {
            ciudad.setNombre(actualizado.getNombre());
        }
        if (actualizado.getImpuestoEntrada() != null) {
            ciudad.setImpuestoEntrada(actualizado.getImpuestoEntrada());
        }
        if (actualizado.getMapaId() != null) {
            Mapa mapa = mapaRepository.findById(actualizado.getMapaId())
                    .orElseThrow(() -> new EntityNotFoundException("Mapa con ID " + actualizado.getMapaId() + " no encontrado"));
            ciudad.setMapa(mapa);
        }

        Ciudad ciudadActualizada = ciudadRepository.save(ciudad);
        return ciudadMapper.toDTO(ciudadActualizada);
    }

    @Override
    @Transactional
    public void eliminarCiudad(Long id) {
        if (!ciudadRepository.existsById(id)) {
            throw new EntityNotFoundException("Ciudad con ID " + id + " no encontrada.");
        }
        ciudadRepository.deleteById(id);
    }
}

