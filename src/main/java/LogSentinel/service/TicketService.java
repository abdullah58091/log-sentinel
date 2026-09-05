package LogSentinel.service;

import LogSentinel.dto.CreateTicketRequest;
import LogSentinel.dto.TicketResponse;
import LogSentinel.entity.Incident;
import LogSentinel.entity.Ticket;
import LogSentinel.entity.TicketStatus;
import LogSentinel.exception.IncidentNotFoundException;
import LogSentinel.exception.TicketNotFoundException;
import LogSentinel.repository.IncidentRepository;
import LogSentinel.repository.TicketRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TicketService {

    private final TicketRepository ticketRepository;
    private final IncidentRepository incidentRepository;

    public TicketService(TicketRepository ticketRepository,
                         IncidentRepository incidentRepository) {
        this.ticketRepository = ticketRepository;
        this.incidentRepository = incidentRepository;
    }

    public TicketResponse createTicket(CreateTicketRequest request) {

        Incident incident = incidentRepository.findById(request.getIncidentId())
                .orElseThrow(() ->
                        new IncidentNotFoundException(
                                "Incident not found with id: "
                                        + request.getIncidentId()
                        ));

        Ticket ticket = new Ticket();

        ticket.setTitle(request.getTitle());
        ticket.setDescription(request.getDescription());
        ticket.setPriority(request.getPriority());
        ticket.setStatus(TicketStatus.OPEN);
        ticket.setCreatedAt(LocalDateTime.now());
        ticket.setIncident(incident);

        Ticket savedTicket = ticketRepository.save(ticket);

        return mapToResponse(savedTicket);
    }

    public List<TicketResponse> getAllTickets() {

        return ticketRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    public TicketResponse getTicketById(Long id) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new TicketNotFoundException(
                                "Ticket not found with id: " + id
                        ));

        return mapToResponse(ticket);
    }

    public TicketResponse updateTicketStatus(
            Long id,
            TicketStatus status) {

        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() ->
                        new TicketNotFoundException(
                                "Ticket not found with id: " + id
                        ));

        ticket.setStatus(status);

        Ticket updatedTicket = ticketRepository.save(ticket);

        return mapToResponse(updatedTicket);
    }

    public void deleteTicket(Long id) {

        if (!ticketRepository.existsById(id)) {
            throw new TicketNotFoundException(
                    "Ticket not found with id: " + id
            );
        }

        ticketRepository.deleteById(id);
    }

    private TicketResponse mapToResponse(Ticket ticket) {

        return new TicketResponse(
                ticket.getId(),
                ticket.getTitle(),
                ticket.getDescription(),
                ticket.getStatus(),
                ticket.getPriority(),
                ticket.getCreatedAt(),
                ticket.getIncident().getId()
        );
    }
}