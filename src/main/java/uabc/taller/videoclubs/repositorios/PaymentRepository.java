package uabc.taller.videoclubs.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import uabc.taller.videoclubs.entidades.Payment;


public interface PaymentRepository extends JpaRepository<Payment, Integer>{
	
}
