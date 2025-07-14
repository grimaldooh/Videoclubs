package uabc.taller.videoclubs.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uabc.taller.videoclubs.entidades.Staff;


public interface StaffRepository extends JpaRepository<Staff, Integer>{

	Staff findByUsername (String userName);
	
	 @Query(value = "select s.email from staff s where s.email ==?1", nativeQuery = true)
	 String findByEmail(String email);

}
