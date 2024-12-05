package com.epa.m.multi.jmprest.model;

public record User(Integer id,
                   String firstName,
                   String lastName,
                   Gender gender,
                   Country country) {
}
