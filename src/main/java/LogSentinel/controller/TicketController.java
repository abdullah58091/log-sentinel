package LogSentinel.controller;

import LogSentinel.dto.CreateTicketRequest;
import LogSentinel.dto.TicketResponse;
import LogSentinel.entity.TicketStatus;
import LogSentinel.service.TicketService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    private final TicketService ticketService;

    public TicketController(TicketService ticketService) {
        this.ticketService = ticketService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TicketResponse createTicket(
            @Valid @RequestBody CreateTicketRequest request) {

        return ticketService.createTicket(request);
    }

    @GetMapping
    public List<TicketResponse> getAllTickets() {

        return ticketService.getAllTickets();
    }

    @GetMapping("/{id}")
    public TicketResponse getTicketById(
            @PathVariable Long id) {

        return ticketService.getTicketById(id);
    }

    @PutMapping("/{id}/status")
    public TicketResponse updateTicketStatus(
            @PathVariable Long id,
            @RequestParam TicketStatus status) {

        return ticketService.updateTicketStatus(id, status);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTicket(@PathVariable Long id) {

        ticketService.deleteTicket(id);
    }
}