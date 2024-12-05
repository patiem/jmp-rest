package com.epa.m.multi.jmprest.model;

public record UserGen(Long id, UserName name, String gender, String email, String nat) {
    public record UserName(String title, String first, String last) {}
}

//gender": "female",
//      "name": {
//        "title": "Miss",
//        "first": "Jennie",
//        "last": "Nichols"
//      },
//"id": {
//        "name": "SSN",
//        "value": "405-88-3636"
//        },
//        "nat": "US"
//"dob": {
//        "date": "1992-03-08T15:13:16.688Z",
//        "age": 30
//        },