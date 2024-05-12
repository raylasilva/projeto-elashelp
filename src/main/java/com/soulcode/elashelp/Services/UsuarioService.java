package com.soulcode.elashelp.Services;

import com.soulcode.elashelp.Models.Login;
import com.soulcode.elashelp.Models.Usuario;
import com.soulcode.elashelp.Repositories.LoginRepository;
import com.soulcode.elashelp.Repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Slf4j
@Service
public class UsuarioService {

    @Autowired
    private  UsuarioRepository usuarioRepository;

    @Autowired
    private  LoginRepository loginRepository;

    @Autowired
    private EmailService emailservice;

    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }

    @Autowired
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }




    public Usuario createUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RuntimeException("Já existe um usuário com este email");
        }if(usuarioRepository.existsByCpf(usuario.getCpf())){
            throw new RuntimeException("Já existe um usuário com este cpf");
        }

        //Salva usuario tanto no model usuario quanto no login
        usuario = usuarioRepository.save(usuario);

        Login login = new Login();
        login.setEmail(usuario.getEmail());
        login.setSenha(usuario.getSenha());
        login.setUsuario(usuario);

        login = loginRepository.save(login);
        usuario.setLogin(login);
        enviarEmailDeBoasVindas(login.getEmail());
        return usuario;
    }

    private void enviarEmailDeBoasVindas(String email) {
        try {
            // Chama o método sendEmail do EmailService
            EmailService.sendEmail(email);
            System.out.println("E-mail de boas-vindas enviado para: " + email);
        } catch (Exception ex) {
            // Trata qualquer exceção de envio de e-mail
            System.err.println("Erro ao enviar e-mail de boas-vindas: " + ex.getMessage());
        }
    }


    public Usuario updateUsuario(Usuario usuario) {
        this.usuarioRepository.findById(usuario.getIdUsuario());
        return usuarioRepository.save(usuario);
    }

    public Usuario deleteById(Long idUsuario) {
        this.usuarioRepository.findById(idUsuario);
        Usuario usuario = new Usuario();
        usuario.setIdUsuario(idUsuario);
        return usuario;
    }


//    //Método que pega o usuário atual (usado na view do navbar)
//    public Usuario getCurrentUser() {
//        return (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//    }

    public String getNomeESobrenomePorCpf(Long idUsuario) {
        Optional<Usuario> usuario = usuarioRepository.findById(idUsuario);
            return usuario.get().getNome() + " " + usuario.get().getSobrenome();
        }
    }



