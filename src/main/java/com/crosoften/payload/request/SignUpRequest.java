package com.crosoften.payload.request;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Classe contendo as variáveis necessárias para a request de criaão de uma conta para o usuário
 */

@Getter
public class SignUpRequest {

    /**
     * Utilizado para identificação do usuário dentro da aplicação
     */
    @NotBlank
    @Size(min = 4, max = 40)
    private String nickname;

    /**
     * Cidade de origem do usuário
     */
    @NotBlank
    private String city;

    /**
     * Gênero do usuário
     *
     * @see com.crosoften.models.Gender
     */
    @NotBlank
    private String gender;

    /**
     * e-mail utilizado pelo usuário
     */
    @NotBlank
    @Size(min = 3, max = 40)
    private String email;

    /**
     * Senha (entre 6 e 20 caracteres)
     */
    @NotBlank
    @Size(min = 6, max = 20)
    private String password;

    /**
     * Setting inicial para a aplicação
     */
    @NotNull
    private boolean enableNotification;


    private MultipartFile[] file;

}
