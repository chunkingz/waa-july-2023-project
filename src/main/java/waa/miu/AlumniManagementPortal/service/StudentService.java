package waa.miu.AlumniManagementPortal.service;

import waa.miu.AlumniManagementPortal.entity.Student;

import java.util.List;

public interface StudentService {

    List<Student> findAll();

    Student findById(Long id);

    Student create(Student student);

    Student update(Long id, Student student);

    void delete(Long id);
}