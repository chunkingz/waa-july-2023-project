package waa.miu.AlumniManagementPortal.dto;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import waa.miu.AlumniManagementPortal.entity.Address;
import waa.miu.AlumniManagementPortal.entity.Student;

import java.time.LocalDateTime;

public class JobAdvertDto {
    private String jobTitle, jobDescription, companyName;
    private double expectedSalary;
    private LocalDateTime dateAdded;
    private Student student;
    private Address address;
}
