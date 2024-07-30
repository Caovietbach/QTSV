package org.example.qtsv.repository;

import org.example.qtsv.entity.Student;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {
    boolean existsByStudentCode(String studentCode);

}


