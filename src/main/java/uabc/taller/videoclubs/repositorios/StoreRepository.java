package uabc.taller.videoclubs.repositorios;

import org.springframework.data.jpa.repository.JpaRepository;

import uabc.taller.videoclubs.entidades.Store;


public interface StoreRepository extends JpaRepository<Store, Integer> {

	public Store findByStoreId(int folio);

}
