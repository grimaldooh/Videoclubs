package uabc.taller.videoclubs.servicios;

import java.util.List;
import java.util.Optional;

import uabc.taller.videoclubs.dto.CatalogoInventarioCliente;
import uabc.taller.videoclubs.dto.DataTable;
import uabc.taller.videoclubs.dto.Paginacion;
import uabc.taller.videoclubs.entidades.Customer;


public interface ICustomerService {

    Paginacion getCustomers(DataTable dataTable);

    Optional<Customer> findById(Integer id);
    List<CatalogoInventarioCliente> filtrarClientePorNombreApellidoEmail(String string);
    List<CatalogoInventarioCliente>  findByCostumerId(Integer costumerId);
}
