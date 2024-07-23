package org.example.qtsv.repository;

import org.example.qtsv.entity.Student;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long> {
    boolean existsByStudentCode(String studentCode);


}