package uabc.taller.videoclubs.servicios;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.CatalogoInventarioCliente;
import uabc.taller.videoclubs.dto.CustomerIndex;
import uabc.taller.videoclubs.dto.DataTable;
import uabc.taller.videoclubs.dto.Paginacion;
import uabc.taller.videoclubs.entidades.Customer;
import uabc.taller.videoclubs.repositorios.CustomerRepository;
import uabc.taller.videoclubs.util.CheckAvailability;

@Service
public class CustomerService implements ICustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Override
    public Paginacion getCustomers(DataTable dataTable) {
     // sort
        Sort.Direction sortDir = dataTable.getOrder().get(0).getOrDefault("dir", "asc").equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC;
        String[] sortBy = dataTable.getOrder().stream()
                .map(i -> i.get("column"))
                .filter(Objects::nonNull)
                .map(i -> {
                    switch (i) {
                        case "0":
                            return List.of("customerId");
                        case "1":
                            return List.of("firstName", "lastName");
                        case "2":
                            return List.of("email");
                        default:
                            return List.of("");
                    }
                }).flatMap(List::stream).toArray(String[]::new);

        // search
        String search = dataTable.getSearch().getOrDefault("value", "").trim();
        String firstName = search;
        String lastName = search;
        if (search.contains(" ")) {
            String[] temp = search.split(" ");
            firstName = temp[0];
            lastName = temp[1];
        }

        //pagination
        Pageable p = PageRequest.of(dataTable.getPage(), dataTable.getLength(), Sort.by(sortDir, sortBy));

        Page<CustomerIndex> result = customerRepository.findAllByFirstNameContainsIgnoreCaseAndLastNameContainsIgnoreCaseOrEmailContainsIgnoreCase(firstName, lastName, search, p);
        long total = customerRepository.count();
        List<List<String>> data = result.stream().map(i -> List.of(
                CheckAvailability.check(i.getCustomerId().toString()),
                CheckAvailability.check(i.getName()),
                CheckAvailability.check(i.getEmail()))
        ).collect(Collectors.toList());

        return Paginacion.builder()
                .draw(dataTable.getDraw())
                .recordsTotal(total)
                .recordsFiltered(result.getTotalElements())
                .data(data)
                .build();
    }

    @Override
    public Optional<Customer> findById(Integer id) {
        return customerRepository.findById(id);
    }

    public Customer save(Customer customer) {
        return customerRepository.save(customer);
    }
    
    @Override
	public List<CatalogoInventarioCliente> filtrarClientePorNombreApellidoEmail(String string) {
		return customerRepository.filtrarClientePorNombreApellidoEmail(string);
	}
	
	@Override
	public List<CatalogoInventarioCliente>  findByCostumerId(Integer costumerid) {
		return customerRepository.findCatByCostumerId(costumerid);
	}
	
    public String findByIdCustumerDetalles(Integer id) {
		return customerRepository.findByCustomerIdDetalles(id);
	}

	public Customer findByIdCustumer(Integer id) {
		return customerRepository.findByCustomerId(id);
	}
	
	public void actualizarEstadoCliente(Boolean activeBool, Integer id) {
		customerRepository.actualizarActive(activeBool, id);
	}
}
