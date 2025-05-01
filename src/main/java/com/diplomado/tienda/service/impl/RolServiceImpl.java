package com.diplomado.tienda.service.impl;

import com.diplomado.tienda.exception.RolNoEncontradoException;
import com.diplomado.tienda.model.Rol;
import com.diplomado.tienda.repository.RolRepository;
import com.diplomado.tienda.service.RolService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RolServiceImpl implements RolService {

    private final RolRepository rolRepository;

    @Override
    public List<Rol> listarRoles() {
        log.info("\n 🔐 Listando todos los roles disponibles... \n");
        List<Rol> roles = rolRepository.findAll()
                .stream()
                .filter(rol -> rol.getNombre() != null)
                .collect(Collectors.toList());

        log.info("\n ✅ Se encontraron {} roles con nombre válido. \n", roles.size());
        return roles;
    }

    @Override
    public Optional<Rol> obtenerRolPorId(Integer idRol) {
        log.info("\n 🔍 Buscando rol por ID: {} \n", idRol);

        return Optional.ofNullable(rolRepository.findById(idRol)
                .orElseThrow(() -> {
                    log.warn("\n ❌ Rol no encontrado con ID: {} \n", idRol);
                    return new RolNoEncontradoException("Rol no encontrado con ID: " + idRol);
                }));
    }

    @Override
    public Optional<Rol> findByNombre(String nombre) {
        log.info("\n 🔎 Buscando rol por nombre: '{}' \n", nombre);
        Optional<Rol> resultado = rolRepository.findByNombre(nombre);

        if (resultado.isEmpty()) {
            log.warn("\n ⚠️ No se encontró ningún rol con el nombre '{}' \n", nombre);
        } else {
            log.info("\n ✅ Rol encontrado: ID={}, Nombre='{}' \n", resultado.get().getId_rol(), resultado.get().getNombre());
        }

        return resultado;
    }
}
