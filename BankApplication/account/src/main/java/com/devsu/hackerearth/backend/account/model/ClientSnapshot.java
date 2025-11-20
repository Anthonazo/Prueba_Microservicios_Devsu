package com.devsu.hackerearth.backend.account.model;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
public class ClientSnapshot {

    @Id
    private Long id;
    private String name;

}