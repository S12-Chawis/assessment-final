package com.projectmanager.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor

public class User {
    private UUID id;
    private String username;
    private String email;
    private String password;
}
