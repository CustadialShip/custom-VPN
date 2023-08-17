package com.dshard.freespace.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RequestFormUser {
    private String firstName;
    private String lastName;
    private String username;
    private String password;
}
