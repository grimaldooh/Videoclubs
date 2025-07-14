package uabc.taller.videoclubs.repositorios;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import uabc.taller.videoclubs.dto.CatalogoInventarioPelicula;
import uabc.taller.videoclubs.entidades.Inventory;



public interface InventoryRepository extends JpaRepository<Inventory, Integer> {


    @Query(value = "SELECT i FROM Inventory i "
            + "INNER JOIN FETCH i.film f "
            + "WHERE i.inventoryId = ?1")
    Inventory findByInventoryId(Integer ticketId);
    
    @Query(value = "select i.inventory_id inventoryId, f.film_id filmId, f.title, f.description, f.release_year releaseYear, TRIM(l.\"name\") as \"language\", f.rental_rate rentalRate "
    		+ " FROM inventory i JOIN film f on i.film_id = f.film_id join \"language\" l on l.language_id  = f.language_id   "
    		+ " where i.store_id  = ?1 and LOWER(f.title) like '%' || LOWER(?2) ||'%'", nativeQuery = true)
    List<CatalogoInventarioPelicula> findByStoreIdAndTitle(Integer store, String titulo);
    
    @Query(value = "select i.inventory_id inventoryId, f.film_id filmId, f.title, f.description, f.release_year releaseYear, TRIM(l.\"name\") as \"language\", f.rental_rate rentalRate "
    		+ " FROM inventory i JOIN film f on i.film_id = f.film_id join \"language\" l on l.language_id  = f.language_id   "
    		+ " where i.inventory_id = ?", nativeQuery = true)
    List<CatalogoInventarioPelicula> findByInventory(Integer inventoryId);
}
