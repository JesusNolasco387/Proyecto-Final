package com.diplomado.tienda.security;


import com.diplomado.tienda.model.Usuario;
import com.diplomado.tienda.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Service
@RequiredArgsConstructor
public class AutenticacionService {

    private final UsuarioService usuarioService;

    // Método para verificar si el Principal es nulo y manejar el error
    private boolean esPrincipalValido(Principal principal, RedirectAttributes redirectAttributes) {
        if (principal == null) {
            redirectAttributes.addFlashAttribute("error", "Debe iniciar sesión para realizar esta acción.");
            return false;
        }
        return true;
    }

    public Usuario obtenerUsuarioDesdePrincipal(Principal principal, RedirectAttributes redirectAttributes) {
        // Uso del nuevo método para verificar si el principal es válido
        if (!esPrincipalValido(principal, redirectAttributes)) {
            return null;
        }

        String nombreUsuario = principal.getName();
       Usuario usuario = usuarioService.obtenerUsuarioPorEmail(nombreUsuario);

        if (usuario == null) {
            redirectAttributes.addFlashAttribute("error", "Usuario no encontrado.");
        }

        return usuario;
    }


}
