package waa.miu.AlumniManagementPortal.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class JobAdvert {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String jobTitle, jobDescription, companyName;

    private double expectedSalary;

    private LocalDateTime dateAdded;

    @JsonBackReference(value = "student-jobAdverts")
    @ManyToOne
    private Student student;

  @OneToOne
   private Address address;

//    @ManyToMany
//    private List<Student> applicants;

//    @ManyToMany
//    private List<Tag> tags;
}
