package waa.miu.AlumniManagementPortal.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import waa.miu.AlumniManagementPortal.entity.Address;
import waa.miu.AlumniManagementPortal.entity.Student;

import java.util.List;


@Repository
public interface AddressRepo extends ListCrudRepository<Address,Long> {
    @Query("SELECT s FROM Address s WHERE s.isDeleted = 'false'")
    List<Address> findAllActiveAddress();
}
