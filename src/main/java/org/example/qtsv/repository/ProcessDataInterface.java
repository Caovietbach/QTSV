package org.example.qtsv.repository;

import org.example.qtsv.entity.Student;
import org.springframework.stereotype.Service;
import java.util.List;

public interface ProcessDataInterface {
    List<Student> processDataByFilteringLastYear(List<Student> students);
    List<Student> processDataBySortingCountry(List<Student> students);
    List<Student> processDataBySortingGPAFromHighToLow(List<Student> students);
    List<Student> processDataBySortingGPAFromHighToLowAndLastName(List<Student> students);
}
