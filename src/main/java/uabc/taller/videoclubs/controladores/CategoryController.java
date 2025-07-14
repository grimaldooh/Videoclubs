package uabc.taller.videoclubs.controladores;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import uabc.taller.videoclubs.dto.Select2.Select2Result;
import uabc.taller.videoclubs.servicios.ICategoryService;

@Controller
@RequestMapping("category")
public class CategoryController {

	@Autowired
	private ICategoryService category;
	
	Logger logger = LoggerFactory.getLogger(getClass());
	
	@GetMapping("select")
	public ResponseEntity<Select2Result> listar(@Param("search") String search){
			
			try {
				Select2Result select = category.select2(search);
				return ResponseEntity.ok(select);
			}catch(Exception e) {
				logger.error(e.getMessage());
			}
			
			return ResponseEntity.internalServerError().build();
		}
}
