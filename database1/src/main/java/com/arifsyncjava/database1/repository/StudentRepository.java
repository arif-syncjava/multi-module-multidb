package com.arifsyncjava.database1.repository;

import com.arifsyncjava.database1.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student,String> {
}
