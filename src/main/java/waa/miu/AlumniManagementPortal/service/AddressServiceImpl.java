package waa.miu.AlumniManagementPortal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import waa.miu.AlumniManagementPortal.entity.Address;
import waa.miu.AlumniManagementPortal.repository.AddressRepo;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AddressServiceImpl implements AddressService {

    private final AddressRepo addressRepo;

    public Address createAddress(Address address) {
        return addressRepo.save(address);
    }

    public Address getAddressById(Long id) {
        return addressRepo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "Address with id "+id+" not found"));
    }

    public Address updateAddress(Address address) {
        if (!addressRepo.existsById(address.getId())) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address with id "+address.getId()+" not found");
        }
        return addressRepo.save(address);
    }

    public void deleteAddress(Long id) {
        if (!addressRepo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Address with id "+id+" not found");
        }
        Address existingAddress = getAddressById(id);
        existingAddress.setIsDeleted(existingAddress.getIsDeleted());
        addressRepo.save(existingAddress);
    }

    public List<Address> getAllAddresses(){
        return addressRepo.findAllActiveAddress();
    }
}
