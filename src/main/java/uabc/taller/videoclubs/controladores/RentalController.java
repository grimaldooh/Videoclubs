package uabc.taller.videoclubs.controladores;

import java.io.IOException;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;

import uabc.taller.videoclubs.dto.CatalogoInventarioCliente;
import uabc.taller.videoclubs.dto.CatalogoInventarioPelicula;
import uabc.taller.videoclubs.dto.RentalDTO;
import uabc.taller.videoclubs.servicios.RentalService;
import uabc.taller.videoclubs.servicios.pdf.RentalPDFExporter;

@Controller
@RequestMapping("rental")
public class RentalController {

    @Autowired
    private RentalService rentalService;

    private HashMap<String, Object> response(Boolean res, Object data, String message) {
        HashMap<String, Object> _response = new HashMap<>();
        _response.put("response", res);
        _response.put("message", message);
        _response.put("data", data);
        return _response;
    }

    @RequestMapping()
    public String rental(Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        return "views/rental/rental";
    }

    @RequestMapping("all")
    @ResponseBody
    public HashMap<String, Object> consultaTodas(Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Principal userLogged = request.getUserPrincipal();
        List<RentalDTO> rentals = rentalService.findAllByUserStore(userLogged.getName());
        return response(true, rentals, "");
    }


    @RequestMapping("pendientes")
    @ResponseBody
    public HashMap<String, Object> consultaPendientes(Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Principal userLogged = request.getUserPrincipal();
        List<RentalDTO> rentals = rentalService.findPendientesByUserStore(userLogged.getName());
        return response(true, rentals, "");
    }
    
    @RequestMapping("rentalId")
    @ResponseBody
    public HashMap<String, Object> consultaRental(Model model, HttpServletRequest request, HttpServletResponse response, Principal principal) {
        Integer rentalId = Integer.parseInt(request.getParameter("rentalId"));
        RentalDTO rental = rentalService.findByRentalId(rentalId);
    	return response(true, rental, "");
    }

    @PostMapping("registrarRenta")
    @ResponseBody
    public HashMap<String, Object> registrarRenta(Model model, HttpServletRequest request, HttpServletResponse response) {
        String msg = "";
        boolean resultado = false;
        Principal userLogged = request.getUserPrincipal();
        RentalDTO _nRental = null;
        try {
            RentalDTO rental = RentalDTO.builder().inventoryId(Integer.parseInt(request.getParameter("inventoryId"))).customerId(Integer.parseInt(request.getParameter("customerId"))).build();
            _nRental = rentalService.registrarRentaYPago(rental, userLogged.getName());
            resultado = _nRental != null;
            msg = resultado ? "Renta registrada" : "Error al procesar los datos";
        } catch (Exception e) {
            msg = "Los datos son invalidos";
        }
        return response(resultado, _nRental, msg);
    }

    @PostMapping("registrarRentas")
    @ResponseBody
    public HashMap<String, Object> registrarRentas(Model model, HttpServletRequest request, HttpServletResponse response) {
        String msg = "";
        boolean resultado = false;
        Principal userLogged = request.getUserPrincipal();
        List<RentalDTO> _nRentals = new ArrayList<>();
        try {
            Integer customerId = Integer.parseInt(request.getParameter("customerId"));
            JSONArray jsonInventory = new JSONArray(request.getParameter("inventory"));
            List<RentalDTO> rentals = new ArrayList<>();
            jsonInventory.forEach(item -> {
                JSONObject itemObject = new JSONObject(item.toString());
                rentals.add(RentalDTO.builder().inventoryId(itemObject.getInt("inventoryId")).build());
            });
            _nRentals = rentalService.registrarRentasYPago(customerId, rentals, userLogged.getName());
            resultado = _nRentals != null;
            msg = resultado ? "Rentas registradas" : "Error al procesar los datos";
        } catch (Exception e) {
            msg = "Los datos son invalidos";
        }
        return response(resultado, _nRentals, msg);
    }
    
    
    @GetMapping("customerSearch")
    @ResponseBody
    public HashMap<String, Object> customerSearch(Model model, HttpServletRequest request, HttpServletResponse response) {
        String search = request.getParameter("search");
        List<CatalogoInventarioCliente>  resultados = null;
        if(search != null) {
        	resultados = rentalService.buscarClientes(search);
        }
    	
        return response(true, resultados, "");
    }
    
    
    @GetMapping("filmSearch")
    @ResponseBody
    public HashMap<String, Object> filmSearch(Model model, HttpServletRequest request, HttpServletResponse response) {
        String search = request.getParameter("search");
        List<CatalogoInventarioPelicula>  resultados = null;
        if(search != null) {
        	Principal user =  request.getUserPrincipal();
        	resultados = rentalService.buscarPeliculas(search,user.getName());
        }
    	
        return response(true, resultados, "");
    }
    
    @GetMapping("export/reciboRenta")
    @ResponseBody
    public void exportPDF(HttpServletRequest request, HttpServletResponse response, @RequestParam Integer rentalId) 
    		throws DocumentException, IOException {
    	RentalDTO rental = rentalService.findByRentalId(rentalId);
    	response.setContentType("application/pdf");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDateTime = dateFormatter.format(new Date());
    	
    	String headerKey = "Content-Disposition";
    	String sbHeaderValue = "attachment; filename=receipt_rental_" + 
    	rental.getRentalId() + "_" + currentDateTime + ".pdf";
    	
    	response.setHeader(headerKey, sbHeaderValue);
    	
    	RentalPDFExporter exporter = new RentalPDFExporter(rental);
    	exporter.export(response);
    }
    
    
}








