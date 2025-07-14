package uabc.taller.videoclubs.servicios;

import java.util.List;

import uabc.taller.videoclubs.dto.CatalogoInventarioPelicula;
import uabc.taller.videoclubs.entidades.Inventory;

public interface IInventoryService {

    Inventory findByInventoryId(Integer inventoryId);
    List<CatalogoInventarioPelicula> filtrarInventarioPorTiendaYTituloPelicula(Integer Store, String titulo);
    List<CatalogoInventarioPelicula>  findByInventory(Integer inventoryId);
}
