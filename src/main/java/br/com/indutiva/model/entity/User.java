package br.com.indutiva.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "USER")
public class User implements Serializable {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo nome Obrigatório")
    private String name;

    @Email(message = "Email inválido")
    @NotBlank(message = "Campo Email obrigatório")
    private String email;

    @NotBlank(message = "Campo Senha obrigatório.")
    private String password;

}
