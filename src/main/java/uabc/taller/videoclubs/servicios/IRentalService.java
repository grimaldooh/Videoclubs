package uabc.taller.videoclubs.servicios;

import uabc.taller.videoclubs.dto.ReturnIndex;
import uabc.taller.videoclubs.entidades.Rental;

import java.util.List;
import java.util.Optional;

public interface IRentalService {
    List<ReturnIndex> returns(Integer id);

    Optional<Rental> findById(Integer rentalId);
}
