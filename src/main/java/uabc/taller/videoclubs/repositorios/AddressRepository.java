package uabc.taller.videoclubs.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import uabc.taller.videoclubs.entidades.Address;


public interface AddressRepository extends JpaRepository<Address, Integer> {
	
	
}
