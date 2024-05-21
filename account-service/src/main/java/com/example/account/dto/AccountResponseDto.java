package com.example.account.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
public class AccountResponseDto {

    private UUID id;

    private String name;

    private String email;

    private String phone;

    private List<UUID> bills;

    private Date creationDate;
}
