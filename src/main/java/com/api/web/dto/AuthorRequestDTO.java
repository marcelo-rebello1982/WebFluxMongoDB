package com.api.web.dto;

import com.api.web.model.EmailModel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.Column;
import javax.validation.Valid;
import javax.validation.constraints.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
public class AuthorRequestDTO implements Serializable {

    private String id;

    @NotEmpty(message = " ") @Length(min =3 , max = 50, message = " campo é obrigatório , tamanho máximo de 50 caracteres")
    private String name;

    @CPF(message = "Atenção : CPF inválido")
    @Column(unique = true, nullable = true)
    @Length(max = 11, message = " campo é obrigatório")
    private String cpf;

    @NotEmpty(message = " ") @Length(min =3 ,max = 50, message = " campo é obrigatório, tamanho máximo de 50 caracteres")
    private String nationality;

    @NotEmpty(message = " ") @Length(min =3 ,max = 250, message = " campo é obrigatório, tamanho máximo de 250 caracteres")
    private String biography;


    @Column @Email(message = " formato de email inválido", flags = {Pattern.Flag.CASE_INSENSITIVE})
    @NotEmpty(message = " ") @Length(min =10 ,max = 250, message = " campo é obrigatório, tamanho máximo de 250 caracteres")
    private String emailString;

    @Valid
    private EmailModel email;

    @Column @NotNull
    @Past(message = " data de nascimento posterior a data atual")
    @JsonFormat(shape = JsonFormat.Shape.STRING , pattern = "dd-MM-yyyy")
    private LocalDate birthdate;


    public AuthorRequestDTO() {
    }

    public AuthorRequestDTO(String id, String name, String cpf, String nationality, String biography, String email, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.nationality = nationality;
        this.biography = biography;
        this.emailString = email;
        this.birthdate = birthdate;
    }

    public AuthorRequestDTO(String id, String name, String cpf, String nationality, String biography, EmailModel email, LocalDate birthdate) {
        this.id = id;
        this.name = name;
        this.cpf = cpf;
        this.nationality = nationality;
        this.biography = biography;
        this.email = email;
        this.birthdate = birthdate;
    }

    //    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }
//
//    public String getName() {
//        return name;
//    }
//
//    public void setName(String name) {
//        this.name = name;
//    }
//
//    public String getNationality() {
//        return nationality;
//    }
//
//    public void setNationality(String nationality) {
//        this.nationality = nationality;
//    }
//
//    public String getBiography() {
//        return biography;
//    }
//
//    public void setBiography(String biography) {
//        this.biography = biography;
//    }
//
//    public LocalDate getBirthdate() {
//        return birthdate;
//    }
//
//    public void setBirthdate(LocalDate birthdate) {
//        this.birthdate = birthdate;
//    }
//
//    public String getCpf() {
//        return cpf;
//    }
//
//    public void setCpf(String cpf) {
//        this.cpf = cpf;
//    }
//
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(String email) {
//        this.email = email;
//    }
}
