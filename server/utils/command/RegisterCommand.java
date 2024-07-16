package com.ficbic.dpeq.utils.command;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterCommand {
    private String username;
    private String email;
    private String password;
}
