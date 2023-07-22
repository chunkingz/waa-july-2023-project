package waa.miu.AlumniManagementPortal.service;

import org.springframework.web.bind.annotation.GetMapping;
import waa.miu.AlumniManagementPortal.dto.StudentDto;
import waa.miu.AlumniManagementPortal.entity.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {

    List<Student> findAll();

    Student findById(Long id);

    List<Student> filterEntities(Map<String, String> filterParams);

    Student create(StudentDto studentDto);

    Student update(Long id, Student student);

    void delete(Long id);

    List<Map<String, Integer>> getStudentsPerState();

    List<Map<String, Integer>> getStudentsPerCity();
}
