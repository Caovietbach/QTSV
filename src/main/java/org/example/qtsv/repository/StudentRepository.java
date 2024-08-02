package org.example.qtsv.repository;

import org.example.qtsv.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {

    boolean existsByStudentCode(String studentCode);

    @Query("SELECT s FROM Student s ORDER BY s.lastName ASC")
    List<Student> findAllByOrderByLastNameAsc();
}