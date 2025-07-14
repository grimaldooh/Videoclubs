package uabc.taller.videoclubs.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.Select2.Select2Data;
import uabc.taller.videoclubs.dto.Select2.Select2Pagination;
import uabc.taller.videoclubs.dto.Select2.Select2Result;
import uabc.taller.videoclubs.entidades.Actor;
import uabc.taller.videoclubs.repositorios.ActorRepository;

@Service
public class ActorService implements IActorService{
	
	@Autowired
	private ActorRepository actorRepository;

	@Override
	public Select2Result select2(String term) {
		Pageable pageable = PageRequest.of(0,10, Sort.by(Sort.Direction.ASC, "actorId"));
		
		String term1 = term;
		String term2 = term;
		
		if (term.trim().contains(" ")) {
			String[] aux = term.split(" ");
			term1 = aux[0];
			term2 = aux[1];
		}
		
		
		Page<Actor> actors = actorRepository.findAllByFirstNameContainsOrLastNameContains(term1, term2, pageable);
		 
		 List<Select2Data> select2Data = actors.stream()
				 .map(i -> new Select2Data(i.getActorId(), 
						 i.getFirstName().concat(" ").concat(i.getLastName())))
				 .collect(Collectors.toList());
		 
		 return Select2Result.builder().
				 results(select2Data).
				 pagination(Select2Pagination
						 .builder()
						 .more(actors.getTotalPages() > 1).build()).
				 build();
	}

}
