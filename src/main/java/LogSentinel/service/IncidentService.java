package LogSentinel.service;

import LogSentinel.dto.CreateIncidentRequest;
import LogSentinel.dto.IncidentResponse;
import LogSentinel.dto.UpdateIncidentRequest;
import LogSentinel.entity.Incident;
import LogSentinel.entity.Log;
import LogSentinel.enums.IncidentStatus;
import LogSentinel.enums.Severity;
import LogSentinel.exception.IncidentNotFoundException;
import LogSentinel.repository.IncidentRepository;
import LogSentinel.repository.LogRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class IncidentService {

    private final IncidentRepository incidentRepository;
    private final LogRepository logRepository;
    private final NotificationService notificationService;

    public IncidentService(
            IncidentRepository incidentRepository,
            LogRepository logRepository,
            NotificationService notificationService) {

        this.incidentRepository = incidentRepository;
        this.logRepository = logRepository;
        this.notificationService = notificationService;
    }

    public IncidentResponse createIncident(
            CreateIncidentRequest request) {

        Log relatedLog = logRepository.findById(
                request.getRelatedLogId()
        ).orElseThrow(() ->
                new IncidentNotFoundException(
                        "Related log not found with id: "
                                + request.getRelatedLogId()
                ));

        Incident incident = new Incident();

        incident.setTitle(request.getTitle());
        incident.setDescription(request.getDescription());
        incident.setSeverity(request.getSeverity());
        incident.setStatus(IncidentStatus.OPEN);
        incident.setRelatedLog(relatedLog);

        LocalDateTime now = LocalDateTime.now();

        incident.setCreatedAt(now);
        incident.setUpdatedAt(now);

        Incident savedIncident =
                incidentRepository.save(incident);

        // Create notification after incident is successfully saved
        notificationService.createIncidentNotification(savedIncident);

        return mapToResponse(savedIncident);
    }

    public IncidentResponse createAutomaticIncident(
            Log relatedLog,
            Severity severity) {

        Incident incident = new Incident();

        incident.setTitle(
                "Automatic Incident: " + relatedLog.getMessage()
        );

        incident.setDescription(
                relatedLog.getMessage()
        );

        incident.setSeverity(severity);
        incident.setStatus(IncidentStatus.OPEN);

        LocalDateTime now = LocalDateTime.now();

        incident.setCreatedAt(now);
        incident.setUpdatedAt(now);

        incident.setRelatedLog(relatedLog);

        Incident savedIncident =
                incidentRepository.save(incident);

        // Create notification after automatic incident is saved
        notificationService.createIncidentNotification(savedIncident);

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

    public IncidentResponse updateIncident(
            Long id,
            UpdateIncidentRequest request) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new IncidentNotFoundException(
                                "Incident not found with id: " + id
                        ));

        incident.setStatus(request.getStatus());
        incident.setUpdatedAt(LocalDateTime.now());

        Incident updatedIncident =
                incidentRepository.save(incident);

        return mapToResponse(updatedIncident);
    }

    public IncidentResponse resolveIncident(Long id) {

        Incident incident = incidentRepository.findById(id)
                .orElseThrow(() ->
                        new IncidentNotFoundException(
                                "Incident not found with id: " + id
                        ));

        incident.setStatus(IncidentStatus.RESOLVED);
        incident.setUpdatedAt(LocalDateTime.now());

        Incident updatedIncident =
                incidentRepository.save(incident);

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

    private IncidentResponse mapToResponse(
            Incident incident) {

        Long relatedLogId =
                incident.getRelatedLog() != null
                        ? incident.getRelatedLog().getId()
                        : null;

        return new IncidentResponse(
                incident.getId(),
                incident.getTitle(),
                incident.getDescription(),
                incident.getSeverity(),
                incident.getStatus(),
                incident.getCreatedAt(),
                incident.getUpdatedAt(),
                relatedLogId
        );
    }
}