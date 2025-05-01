package com.diplomado.tienda.dto;

import lombok.Data;

@Data
public class AuthRequest {

    private String email;
    private String password;

}
