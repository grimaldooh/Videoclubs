package uabc.taller.videoclubs.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import uabc.taller.videoclubs.dto.ReturnIndex;
import uabc.taller.videoclubs.entidades.Rental;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface RentalRepository extends JpaRepository<Rental, Integer> {
    @Query(value = "select r.return_date, f.title from rental r inner join inventory i ON i.inventory_id = r.inventory_id inner join film f on f.film_id = i.film_id where r.inventory_id = ?1", nativeQuery = true)
    List<ReturnIndex> returns(Integer id);

    Optional<Rental> findById(Integer rentalId);
    
    Rental findByRentalId(Integer rentalId);
    
    @Query(value = "SELECT r.rental_id, r.rental_date, r.inventory_id, r.customer_id, r.return_date, r.staff_id, r.last_update "
    		+ " FROM rental r JOIN inventory i ON i.inventory_id  = r.inventory_id "
    		+ " WHERE i.store_id  = ?", nativeQuery = true)
    List<Rental> findAllBySotoreId(Integer storeId);
    
    @Query(value = "SELECT r.rental_id, r.rental_date, r.inventory_id, r.customer_id, r.return_date, r.staff_id, r.last_update "
    		+ " FROM rental r JOIN inventory i ON i.inventory_id  = r.inventory_id "
    		+ " WHERE i.store_id  = ? and r.return_date is null", nativeQuery = true)
    List<Rental> findPendientesBySotoreId(Integer storeId);
    
    @Transactional
	@Modifying
	@Query("update Rental r set r.returnDate = ?2 WHERE r.rentalId = ?1")
    void actualizarFechaEntrega(Integer id, Date returnDate);
    
    
    @Query(value = "select f.rental_duration as duracionMaxRenta "
    		+ "from rental r "
    		+ "join inventory i on i.inventory_id = r.inventory_id "
    		+ "join film f on f.film_id = i.film_id "
    		+ "where r.rental_id = ?1", nativeQuery = true)
    Integer obtenerDuracionRentaParaDevolver(Integer rentalid);
    @Query(value = "select r.rental_id, r.rental_date, r.inventory_id, r.customer_id, r.return_date, r.staff_id, r.last_update "
    		+ "from rental r "
    		+ "join inventory i on i.inventory_id  = r.inventory_id "
    		+ "where customer_id = ?1 "
    		+ "and DATE(rental_date) <= DATE('now()') "
    		+ "and return_date is null "
    		+ "order by rental_date desc", nativeQuery = true)
    List<Rental> obtenerPeliculasParaDevolver(Integer customerId);
    @Query(value = "select r.rental_id, r.rental_date, r.inventory_id, r.customer_id, r.return_date, r.staff_id, r.last_update "
    		+ "from rental r "
    		+ "join inventory i on i.inventory_id  = r.inventory_id "
    		+ "where customer_id = ?1 "
    		+ "and DATE(rental_date) <= DATE('now()') "
    		+ "and return_date is not null "
    		+ "order by rental_date desc", nativeQuery = true)
    List<Rental> obtenerPeliculasDevueltas(Integer customerId);
}
