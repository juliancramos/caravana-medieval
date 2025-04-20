package web.app.caravanamedieval.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import web.app.caravanamedieval.dto.TravelDTO;
import web.app.caravanamedieval.service.TravelService;

@RestController
@RequestMapping("/travel")
public class TravelController {

    private final TravelService travelService;

    public TravelController(TravelService travelService) {
        this.travelService = travelService;
    }

    @PostMapping
    public ResponseEntity<Void> travel(@RequestBody TravelDTO travelDTO) {
        travelService.travel(travelDTO);
        return ResponseEntity.ok().build();
    }
}
