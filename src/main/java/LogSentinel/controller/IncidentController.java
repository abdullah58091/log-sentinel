package LogSentinel.controller;

import LogSentinel.dto.CreateIncidentRequest;
import LogSentinel.dto.IncidentResponse;
import LogSentinel.dto.UpdateIncidentRequest;
import LogSentinel.service.IncidentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
public class IncidentController {

    private final IncidentService incidentService;

    public IncidentController(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public IncidentResponse createIncident(
            @Valid @RequestBody CreateIncidentRequest request) {

        return incidentService.createIncident(request);
    }

    @GetMapping
    public List<IncidentResponse> getAllIncidents() {

        return incidentService.getAllIncidents();
    }

    @GetMapping("/{id}")
    public IncidentResponse getIncidentById(
            @PathVariable Long id) {

        return incidentService.getIncidentById(id);
    }

    @PutMapping("/{id}")
    public IncidentResponse updateIncident(
            @PathVariable Long id,
            @Valid @RequestBody UpdateIncidentRequest request) {

        return incidentService.updateIncident(id, request);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIncident(
            @PathVariable Long id) {

        incidentService.deleteIncident(id);
    }
}