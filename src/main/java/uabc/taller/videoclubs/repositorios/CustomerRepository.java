package uabc.taller.videoclubs.repositorios;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.transaction.annotation.Transactional;

import uabc.taller.videoclubs.dto.CatalogoInventarioCliente;
import uabc.taller.videoclubs.dto.CustomerIndex;
import uabc.taller.videoclubs.entidades.Customer;


public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query(
            value = "select customer_id as customerId, first_name as firstName, last_name as lastName, email " +
                    "from customer " +
                    "where (concat(first_name, ' ', last_name) like '%:filtro%' or email like '%:filtro%')",
            countQuery = "select count(*) from customer " +
                    "where (concat(first_name, ' ', last_name) like '%:filtro%' or email like '%:filtro%')",
            nativeQuery = true)
    Page<CustomerIndex> findAllByOrderByCustomerId(@Param("filtro") String filtro, Pageable pageable);

    Page<CustomerIndex> findAllByFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCaseOrEmailContainsIgnoreCase(String nombre, String apellido, String correo, Pageable pageable);

    Optional<Customer> findById(Integer id);
    
    @Query(value = "SELECT customer_id customerId, first_name || ' ' || last_name as name, email "
    		+ " FROM customer where activebool = 't' and ( (LOWER(first_name) like '%' || LOWER(?1) ||'%') or (LOWER(last_name) like '%' || LOWER(?1) ||'%') or (LOWER(email) like '%' || LOWER(?1) ||'%') )", nativeQuery = true)
    List<CatalogoInventarioCliente> filtrarClientePorNombreApellidoEmail(String titulo);
    
    @Query(value = "SELECT customer_id customerId, first_name || ' ' || last_name as name, email "
    		+ " FROM customer where activebool = 't' and customer_id = ?1", nativeQuery = true)
    List<CatalogoInventarioCliente> findCatByCostumerId(Integer costumerId);
    
    @Query(value = "select c.first_name, c.last_name, c.email, a.phone, c3.country_id, c3.country, c2.city_id, c2.city, a.address, a.postal_code, s.name from customer c "
    +"join address a on a.address_id = c.address_id "
    +"join city c2 on c2.city_id = a.city_id "
    +"join country c3 on c3.country_id = c2.country_id "
    +"join store s on s.store_id = c.store_id "
    +"where c.customer_id = ?1", nativeQuery = true)
    String findByCustomerIdDetalles(Integer id);
    
    Customer findByCustomerId(Integer id);

    @Transactional
	@Modifying
	@Query("update Customer c set c.activeBool = ?1 WHERE c.customerId = ?2")
    void actualizarActive(Boolean activebool, Integer id);
}
