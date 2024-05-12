package com.soulcode.elashelp.Services;

import com.soulcode.elashelp.Models.Status;
import com.soulcode.elashelp.Models.Ticket;
import com.soulcode.elashelp.Repositories.TicketRepository;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    public List<Ticket> findAllTickets() {
        return ticketRepository.findAll();
    }

    public Optional<Ticket> findTicketById(Integer id) {
        return ticketRepository.findById(id);
    }

    public Ticket saveTicket(Ticket ticket) {
        return ticketRepository.save(ticket);
    }

    public Ticket updateTicket(Integer id, Ticket ticketDetails) {
        Ticket ticket = ticketRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Ticket not found with id " + id));

        ticket.setTitulo(ticketDetails.getTitulo());
        ticket.setDescricao(ticketDetails.getDescricao());
        ticket.setStatus(ticketDetails.getStatus());
        ticket.setPrioridade(ticketDetails.getPrioridade());
        ticket.setData(ticketDetails.getData());
        ticket.setSetor(ticketDetails.getSetor());

        return ticketRepository.save(ticket);
    }

    public void deleteTicket(Integer id) {
        ticketRepository.deleteById(id);
    }


//    Requisições para os gráficos
    public Map<String, Long > getOpenTicketsBySector() {
        List<Ticket> tickets = ticketRepository.findAll();

        Map<String, Long> openTicketsBySector = tickets.stream()
                .filter(ticket -> Status.ABERTO.equals(ticket.getStatus()))
                .collect(Collectors.groupingBy(ticket -> ticket.getSetor().toString(), Collectors.counting()));

        log.info("Open tickets by sector: {}", openTicketsBySector);

        return openTicketsBySector;
    }

    public Map<String, Long> getTotalTicketsBySector() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getSetor().toString(), Collectors.counting()));
    }

    public Map<String, Long> getFinishedTicketsBySector() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .filter(ticket -> Status.FINALIZADO.equals(ticket.getStatus()))
                .collect(Collectors.groupingBy(ticket -> ticket.getSetor().toString(), Collectors.counting()));
    }

    public Map<String, Long> getTicketsByStatus() {
        List<Ticket> tickets = ticketRepository.findAll();

        return tickets.stream()
                .collect(Collectors.groupingBy(ticket -> ticket.getStatus().toString(), Collectors.counting()));
    }


}