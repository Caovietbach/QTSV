package org.example.qtsv.repository;

import org.example.qtsv.entity.LastYearStudent;
import org.example.qtsv.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

public interface StudentRepository extends CrudRepository<Student, Long>, PagingAndSortingRepository<Student, Long> {

    boolean existsByStudentCode(String studentCode);


    @Query("SELECT s FROM Student s WHERE " +
            "(:firstName IS NULL OR s.firstName LIKE %:firstName%) AND " +
            "(:lastName IS NULL OR s.lastName LIKE %:lastName%) AND " +
            "(:age = 0 OR s.age = :age) AND " +
            "(:studentCode IS NULL OR s.studentCode LIKE %:studentCode%) AND " +
            "(:year = 0 OR s.year = :year) AND " +
            "(:major IS NULL OR s.major = :major) AND " +
            "(:country IS NULL OR s.country = :country)")
    List<Student> searchBy(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("age") int age,
            @Param("studentCode") String studentCode,
            @Param("year") int year,
            @Param("major") String major,
            @Param("country") String country);


}