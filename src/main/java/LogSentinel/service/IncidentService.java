package LogSentinel.service;

import LogSentinel.dto.CreateIncidentRequest;
import LogSentinel.dto.IncidentResponse;
import LogSentinel.entity.Incident;
import LogSentinel.entity.IncidentStatus;
import LogSentinel.exception.IncidentNotFoundException;
import LogSentinel.repository.IncidentRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;

    public IncidentService(IncidentRepository incidentRepository) {
        this.incidentRepository = incidentRepository;
    }

    public IncidentResponse createIncident(CreateIncidentRequest request) {

        Incident incident = new Incident();

        incident.setTitle(request.getTitle());
        incident.setDescription(request.getDescription());
        incident.setStatus(IncidentStatus.OPEN);
        incident.setCreatedAt(LocalDateTime.now());

        Incident savedIncident = incidentRepository.save(incident);

        return mapToResponse(savedIncident);
    }

    public List<IncidentResponse> getAllIncidents() {

        return incidentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public IncidentResponse getIncidentById(Long id) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new IncidentNotFoundException(
                                "Incident not found with id: " + id
                        ));

        return mapToResponse(incident);
    }

    public IncidentResponse resolveIncident(Long id) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new IncidentNotFoundException(
                                "Incident not found with id: " + id
                        ));

        incident.setStatus(IncidentStatus.RESOLVED);

        Incident updatedIncident = incidentRepository.save(incident);

        return mapToResponse(updatedIncident);
    }

    public void deleteIncident(Long id) {

        if (!incidentRepository.existsById(id)) {
            throw new IncidentNotFoundException(
                    "Incident not found with id: " + id
            );
        }

        incidentRepository.deleteById(id);
    }

    private IncidentResponse mapToResponse(Incident incident) {

        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getStatus(),
                incident.getCreatedAt()
        );
    }
}