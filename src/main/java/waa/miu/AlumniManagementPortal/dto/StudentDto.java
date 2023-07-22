package waa.miu.AlumniManagementPortal.dto;

import lombok.Data;
import waa.miu.AlumniManagementPortal.entity.Address;
import waa.miu.AlumniManagementPortal.entity.Role;

@Data
public class StudentDto {
    private Long id;

    private String firstName, lastName, email, password, phone, studentId, cv, isCurrentlyEmployed, isDeleted;
    private Address address;
    private Role role;
}

