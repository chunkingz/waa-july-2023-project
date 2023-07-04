package waa.miu.AlumniManagementPortal.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import waa.miu.AlumniManagementPortal.entity.Address;
import waa.miu.AlumniManagementPortal.repository.AddressRepo;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    public AddressRepo addressRepo;

    public AddressServiceImpl(AddressRepo addressRepo){
        this.addressRepo=addressRepo;
    }

    public Address createAddress(Address address) {
        return addressRepo.save(address);
    }

    public Address getAddressById(Long id) {
        return addressRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address not found with id: " + id));
    }

    public Address updateAddress(Address address) {
        if (!addressRepo.existsById(address.getId())) {
            throw new EntityNotFoundException("Address not found with id: " + address.getId());
        }
        return addressRepo.save(address);
    }

    public void deleteAddress(Long id) {
        if (!addressRepo.existsById(id)) {
            throw new EntityNotFoundException("Address not found with id: " + id);
        }
        addressRepo.deleteById(id);
    }
    public List<Address> getAllAddresses(){
        return addressRepo.findAll();
    }
}