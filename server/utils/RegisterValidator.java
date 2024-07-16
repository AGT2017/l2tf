package com.ficbic.dpeq.utils;

import com.ficbic.dpeq.pojo.User;
import com.ficbic.dpeq.utils.command.RegisterCommand;
import org.apache.shiro.lang.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import java.util.regex.Pattern;


public class RegisterValidator implements Validator {

    private static final String SIMPLE_EMAIL_REGEX = "[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}";

    public boolean supports(Class aClass) {
        return RegisterCommand.class.isAssignableFrom(aClass);
    }

    public void validate(Object o, Errors errors) {
        RegisterCommand command = (RegisterCommand) o;
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "error.username.empty", "Please specify a username.");
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "email", "error.email.empty", "Please specify an email address.");
        if (StringUtils.hasText(command.getEmail()) && !Pattern.matches(SIMPLE_EMAIL_REGEX, command.getEmail().toUpperCase())) {
            errors.rejectValue("email", "error.email.invalid", "Please enter a valid email address.");
        }
        ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "error.password.empty", "Please specify a password.");
    }
}