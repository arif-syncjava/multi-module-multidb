package com.arifsyncjava.data;

import com.arifsyncjava.database1.model.Student;
import com.arifsyncjava.database1.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class Database1 implements CommandLineRunner {

    private final StudentRepository  studentRepository;

    @Override
    public void run(String... args) throws Exception {
        Student student = new Student(
                "123","robin","ahmed","math");
        studentRepository.save(student);

    }
}
