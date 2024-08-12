package org.example.qtsv.repository;

import org.example.qtsv.entity.LastYearStudentEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LastYearStudentRepository extends CrudRepository<LastYearStudentEntity, Long> {

    @Query("SELECT lys FROM LastYearStudentEntity lys WHERE " + "(:thesisTitle IS NULL OR lys.thesisTitle LIKE %:thesisTitle%)")
    List<LastYearStudentEntity> searchThesisBy(@Param("thesisTitle") String thesisTitle);
}
