package uabc.taller.videoclubs.servicios;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.CatalogoInventarioPelicula;
import uabc.taller.videoclubs.entidades.Inventory;
import uabc.taller.videoclubs.repositorios.InventoryRepository;


@Service
public class InventoryService implements IInventoryService {

    @Autowired
    private InventoryRepository inventoryRepository;

    @Override
    public Inventory findByInventoryId(Integer inventoryId) {
        return inventoryRepository.findByInventoryId(inventoryId);
    }
	
	@Override
	public List<CatalogoInventarioPelicula> filtrarInventarioPorTiendaYTituloPelicula(Integer StoreId, String titulo) {
		return inventoryRepository.findByStoreIdAndTitle(StoreId, titulo);
	}
	
	@Override
	public List<CatalogoInventarioPelicula>  findByInventory(Integer inventoryId) {
		return inventoryRepository.findByInventory(inventoryId);
	}
}
