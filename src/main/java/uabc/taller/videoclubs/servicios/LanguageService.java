package uabc.taller.videoclubs.servicios;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import uabc.taller.videoclubs.dto.Select2.Select2Data;
import uabc.taller.videoclubs.dto.Select2.Select2Pagination;
import uabc.taller.videoclubs.dto.Select2.Select2Result;
import uabc.taller.videoclubs.entidades.Language;
import uabc.taller.videoclubs.repositorios.LanguageRepository;

@Service
public class LanguageService implements ILanguageService {
	@Autowired
	private LanguageRepository languageRepository;

	@Override
	public Select2Result select2(String term) {
		
		List<Language> language = languageRepository.findAll();
		 
		 List<Select2Data> select2Data = language.stream()
				 .map(i -> new Select2Data(i.getLanguageId(), 
						 i.getName()))
				 .collect(Collectors.toList());
		 
		 return Select2Result.builder().
				 results(select2Data).
				 pagination(Select2Pagination
						 .builder()
						 .more(false).build()).
				 build();
	}

}
