package com.diplomado.tienda.controller;


import com.diplomado.tienda.dto.AuthRequest;
import com.diplomado.tienda.dto.AuthResponse;
import com.diplomado.tienda.dto.UserRegistrationDTO;
import com.diplomado.tienda.security.JwtTokenProvider;
import com.diplomado.tienda.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/jwt")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authRequest.getEmail(),
                        authRequest.getPassword()
                )
        );

        String token = jwtTokenProvider.generateToken(authentication);


        ResponseCookie jwtCookie = ResponseCookie.from("token", token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(3600)
                .sameSite("Strict")
                .build();


        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString())
                .body(new AuthResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserRegistrationDTO userDTO) {
        log.info("\n 📝 Solicitud de registro para nuevo usuario: {} \n", userDTO.getUsername());
        try {
            usuarioService.registerNewUser(userDTO);
            log.info("\n ✅ Usuario '{}' registrado exitosamente. \n", userDTO.getUsername());
            return ResponseEntity.ok("Usuario registrado exitosamente");
        } catch (Exception e) {
            log.error("\n ❌ Error al registrar usuario '{}': {} \n", userDTO.getUsername(), e.getMessage());
            return ResponseEntity.status(400).body("Error en el registro: " + e.getMessage());
        }
    }

}
