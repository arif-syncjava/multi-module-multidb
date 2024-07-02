package com.arifsyncjava.database1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor @AllArgsConstructor
@Entity (name = "students")
public class Student {
    @Id
    private String id;
    private String firstName;
    private String lastName;
    private String department;
}
