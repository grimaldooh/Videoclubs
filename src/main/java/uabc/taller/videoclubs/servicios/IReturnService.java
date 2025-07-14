package uabc.taller.videoclubs.servicios;

import java.util.List;

import uabc.taller.videoclubs.dto.RentalDTO;

public interface IReturnService {
	
	public List<RentalDTO> obtenerPeliculasParaDevolver(Integer parametro);
	
	public Integer obtenerDuracionRentaParaDevolver(Integer rentaId);

	List<RentalDTO> obtenerPeliculasDevueltas(Integer parametro);
	
	public String registrarDevolucion(List<RentalDTO> rentals,  String returnDate, String multaGenerada, 
			Integer customerId);

}
