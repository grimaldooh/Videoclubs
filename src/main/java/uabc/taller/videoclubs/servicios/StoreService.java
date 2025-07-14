package uabc.taller.videoclubs.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.Select2.Select2Data;
import uabc.taller.videoclubs.dto.Select2.Select2Pagination;
import uabc.taller.videoclubs.dto.Select2.Select2Result;
import uabc.taller.videoclubs.entidades.Store;
import uabc.taller.videoclubs.repositorios.StoreRepository;

@Service
public class StoreService implements IStoreService{
	@Autowired
	private StoreRepository storeRepository;
	
	@Override
	public List<Store> findAll() {
		return storeRepository.findAll();
	}
	
	public Store obtenerTiendaPorId(Integer id) {
		return storeRepository.findByStoreId(id);
	}
	
	public Select2Result select2() {

        List<Store> all = storeRepository.findAll();

        List<Select2Data> data = all.stream().
                map(i -> new Select2Data(i.getStoreId(), i.getName())).
                collect(Collectors.toList());

        return Select2Result.builder().
                results(data).
                pagination(Select2Pagination.builder().more(Boolean.FALSE).build()).
                build();

    }

}
