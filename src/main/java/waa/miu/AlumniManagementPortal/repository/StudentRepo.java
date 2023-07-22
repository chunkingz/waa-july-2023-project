package waa.miu.AlumniManagementPortal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import waa.miu.AlumniManagementPortal.entity.Student;

import java.util.List;

@Repository
public interface StudentRepo extends ListCrudRepository<Student, Long> {

    @Query("SELECT s FROM Student s WHERE s.isDeleted = 'false'")
    List<Student> findAllActiveStudents();
    List<Student> findByAddressState(String state);
    List<Student> findByAddressCity(String city);
    List<Student> findAllByMajorMajorName(String major);
    List<Student> findAllByFirstName(String name);
    List<Student> findByStudentId(String studentId);
}
