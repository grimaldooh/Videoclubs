package uabc.taller.videoclubs.servicios.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.draw.DottedLineSeparator;

import uabc.taller.videoclubs.dto.RentalDTO;
import uabc.taller.videoclubs.util.QRCode;


public class RentalPDFExporter {
    private final RentalDTO rental;

    public RentalPDFExporter(RentalDTO rental) {
        this.rental = rental;
    }

    public void export(HttpServletResponse response) throws DocumentException, IOException {
    	Document document = new Document(new Rectangle(216, 500), 5, 5, 10, 10);
        PdfWriter instance = PdfWriter.getInstance(document, response.getOutputStream());

        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy hh:mm a");

        document.open();

        Font fontTitle = FontFactory.getFont(FontFactory.COURIER, 14);

        Font fontBodyBold = FontFactory.getFont(FontFactory.COURIER_BOLD, 12);
        Font fontBody = FontFactory.getFont(FontFactory.COURIER, 12);

        DottedLineSeparator separator = new DottedLineSeparator();
        separator.setPercentage(59500f / 523f);
        Chunk linebreak = new Chunk(separator);

        InputStream template = getClass().getClassLoader()
                .getResourceAsStream("static/assets/imgs/sakila-logo1.jpg");

        if (template != null) {
            Image logo = Image.getInstance(template.readAllBytes());
            logo.scaleAbsolute(75, 75);
            logo.setAlignment(Element.ALIGN_CENTER);
            document.add(logo);
        }

        Paragraph pTitle = new Paragraph("Sakila Video Club", fontTitle);
        pTitle.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pTitle);

        document.add(linebreak);

        Paragraph renta = new Paragraph("Renta #" + rental.getRentalId(), fontTitle);
        renta.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(renta);

        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("FECHA DE RENTA:", fontBodyBold));
        document.add(new Paragraph(dateFormat.format(new Date(rental.getRentalDate().getTime())), fontBody));

        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("CLIENTE:", fontBodyBold));
        document.add(new Paragraph(String.format("%d - %s", rental.getCustomerId(), Arrays.stream(rental.getNombreCliente().toLowerCase().split(" "))
                .map(nombre -> nombre.substring(0, 1).toUpperCase() + nombre.substring(1)).collect(Collectors.joining(" "))), fontBody));

        document.add(Chunk.NEWLINE);
        document.add(new Paragraph("PELÍCULA:", fontBodyBold));
        document.add(new Paragraph(String.format("%s (#%d)", rental.getTituloPelicula(), rental.getInventoryId()), fontBody));

        document.add(Chunk.NEWLINE);

        if (rental.getReturnDate() != null) {
            document.add(new Paragraph("FECHA DE REGRESO:", fontBodyBold));
            document.add(new Paragraph(dateFormat.format(new Date(rental.getReturnDate().getTime())), fontBody));
            document.add(Chunk.NEWLINE);
        } else {
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);
        }

        fontBodyBold.setSize(10);
        fontBody.setSize(10);

        document.add(Chunk.NEWLINE);

        Paragraph pLastUpdateLabel = new Paragraph("ÚLTIMA ACTUALIZACIÓN:", fontBodyBold);
        pLastUpdateLabel.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pLastUpdateLabel);

        Paragraph pLastUpdate = new Paragraph(dateFormat.format(new Date(rental.getLastUpdate().getTime())), fontBody);
        pLastUpdate.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(pLastUpdate);

        QRCode code = new QRCode();
        Optional<Image> image = code.generateBarcode(String.format("R%dF%dC%d", rental.getRentalId(), rental.getInventoryId(), rental.getCustomerId()), instance.getDirectContent());
        if (image.isPresent()) {
            image.get().scaleAbsolute(156, 41);
            image.get().setAbsolutePosition(30, document.bottom());
            document.add(image.get());
        }

        document.close();
    }
}









