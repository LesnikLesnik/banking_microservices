package com.example.bill.dto.account;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountResponseDto {

    private UUID id;

    private String name;

    private String email;

    private String phone;

    private List<UUID> bills;

    private Date creationDate;
}
