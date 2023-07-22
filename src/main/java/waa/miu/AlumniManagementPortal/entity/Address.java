package waa.miu.AlumniManagementPortal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String street, city, state, zipcode, isDeleted;

//    @JsonBackReference(value = "faculty-address")
//    @OneToOne
//    private Faculty faculty;
//
//    @JsonBackReference(value = "currentWorkPlace-address")
//    @OneToOne
//    private CurrentWorkPlace currentWorkPlace;
//
//    @JsonBackReference(value = "student-address")
//    @OneToOne
//    private Student student;


}

