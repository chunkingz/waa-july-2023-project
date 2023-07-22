package waa.miu.AlumniManagementPortal.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
public class AlumniUser {
    @Id
    @GeneratedValue
    private Long id;

    private String firstName, lastName, email, phone, isDeleted;

    @JsonIgnore
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

}
