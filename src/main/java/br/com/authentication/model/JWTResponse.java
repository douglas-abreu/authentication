package br.com.authentication.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class JWTResponse {
    private String login;
    private String name;
    private Boolean authenticated;
}
