package com.ficbic.dpeq.utils.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginCommand {
    private String username;
    private String password;
    private boolean rememberMe;
}
