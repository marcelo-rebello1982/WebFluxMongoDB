//package com.api.web.validator;
//
//import lombok.Data;
//import org.hibernate.validator.constraints.Length;
//
//import javax.validation.constraints.NotBlank;
//
//@Data
//@PasswordEqualsConfirmPassword
//@PasswordNotEqualsEmail
//public class RegisterForm implements PasswordEqualsConfirmPassword.Model, PasswordNotEqualsEmail.Model {
//
//    @NotBlank(message = "Required")
//    @EmailValidator(message = "Invalid format")
//    private String email;
//
//    @NotBlank(message = "Required")
//    @AtLeastPassword
//    @Length(min = 8, max = 50, message = "At least {min} characters")
//    private String password;
//
//    @NotBlank(message = "Required")
//    @Length(min = 8, max = 50, message = "At least {min} characters")
//    private String confirmPassword;
//
//}