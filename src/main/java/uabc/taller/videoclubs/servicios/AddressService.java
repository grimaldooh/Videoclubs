package uabc.taller.videoclubs.servicios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.entidades.Address;
import uabc.taller.videoclubs.repositorios.AddressRepository;

@Service
public class AddressService   {
    @Autowired
    private AddressRepository addressRepository;

    

    public Address save(Address address) {
        return addressRepository.save(address);
    }
    
   
}
