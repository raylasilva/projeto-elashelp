package com.soulcode.elashelp.Controllers;

import com.soulcode.elashelp.Models.*;
import com.soulcode.elashelp.Services.LoginService;
import com.soulcode.elashelp.Services.TecnicoService;
import com.soulcode.elashelp.Services.TicketService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@ControllerAdvice
public class GlobalControllerAdvice {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private TecnicoService tecnicoService;

    @ModelAttribute("nome")
    public String getNome() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            String email = ((UserDetails) authentication.getPrincipal()).getUsername();
            return loginService.getUsuarioOuTecnico(email);
        }
        return "ao KittyHelp";
    }

    @ModelAttribute
    public void showAllTickets(Model model) {
        List<Ticket> tickets = ticketService.findAllTickets();
        model.addAttribute("allTickets", tickets);
    }

    @ModelAttribute
    public void showAllTecnicos(Model model) {
        List<Tecnico> tecnicos = tecnicoService.findAll();
        model.addAttribute("tecnicos", tecnicos);
    }

    @ModelAttribute
    public void showTicketById(Model model, @RequestParam(value = "id", required = false) Integer id) {
        if (id != null) {
            Ticket ticket = ticketService.findAllTickets().get(id);
            model.addAttribute("ticket", ticket);
        }
    }

    @ModelAttribute
    public void showTecId(Model model, @RequestParam(value = "id", required = false) Long idTecnico) {
        if (idTecnico != null) {
            Tecnico tecnico = tecnicoService.findById(idTecnico);
            model.addAttribute("idTecnico", idTecnico);
        }
    }

}