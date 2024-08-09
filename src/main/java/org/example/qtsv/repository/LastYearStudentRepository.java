package org.example.qtsv.repository;

import org.example.qtsv.entity.LastYearStudent;
import org.example.qtsv.entity.Student;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LastYearStudentRepository extends CrudRepository<LastYearStudent, Long> {

    @Query("SELECT lys FROM LastYearStudent lys WHERE " + "(:thesisTitle IS NULL OR lys.thesisTitle LIKE %:thesisTitle%)")
    List<LastYearStudent> searchThesisBy(@Param("thesisTitle") String thesisTitle);
}
