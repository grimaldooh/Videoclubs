package uabc.taller.videoclubs.servicios;

import uabc.taller.videoclubs.dto.Select2.Select2Result;

public interface IActorService {

	Select2Result select2(String term);
	
}
