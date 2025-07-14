package uabc.taller.videoclubs.controladores;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.itextpdf.text.DocumentException;

import uabc.taller.videoclubs.dto.RentalDTO;
import uabc.taller.videoclubs.dto.TicketModel;
import uabc.taller.videoclubs.entidades.Ticket;
import uabc.taller.videoclubs.repositorios.CustomerRepository;
import uabc.taller.videoclubs.servicios.TicketService;
import uabc.taller.videoclubs.servicios.pdf.ReceiptPDFExporter;
import uabc.taller.videoclubs.servicios.pdf.RentalPDFExporter;

@Controller
@RequestMapping("tickets")
public class TicketController {
	
	@Autowired
	TicketService ticketService;
	
	
	
	private HashMap<String, Object> response(Boolean res, Object data, String message){
    	HashMap<String, Object> _response = new HashMap<>();
    	_response.put("response", res);
    	_response.put("message", message);
    	_response.put("data", data);
    	return _response;
    }
	
	@RequestMapping("")
	public String multas(Model model, HttpServletRequest request, HttpServletResponse response) {
		return "views/multa";
	}
	
	@GetMapping("obtener")
	@ResponseBody
	public HashMap<String, Object> obtenerTickets(HttpServletRequest request, 
			HttpServletResponse response, Integer customerId) {
		List<TicketModel> tickets = ticketService.findFromCustomerCustom(customerId);
		return response(true, tickets, "");
	}
	
	@GetMapping("pagar")
	@ResponseBody
	public Map<String, Object> pagar(HttpServletRequest request,Integer ticketId){
		Map<String,Object> response =new HashMap<>();
		ticketService.pagar(ticketId,response);
		return response;
		
	}
	
	@GetMapping("export/reciboMulta")
    @ResponseBody
    public void exportPDF(HttpServletRequest request, HttpServletResponse response) 
    		throws DocumentException, IOException {
    	Ticket ticket = ticketService.consultarTicket(Integer.parseInt(request.getParameter("ticketId")));
    	response.setContentType("application/pdf");
    	DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss");
    	String currentDateTime = dateFormatter.format(new Date());
    	
    	String headerKey = "Content-Disposition";
    	String sbHeaderValue = "attachment; filename=receipt_multa_" + 
    	ticket.getTicketId() + "_" + currentDateTime + ".pdf";
    	
    	response.setHeader(headerKey, sbHeaderValue);
    	
    	ReceiptPDFExporter exporter = new ReceiptPDFExporter(ticket);
    	exporter.export(response);
    }
	
}










