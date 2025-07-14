package uabc.taller.videoclubs.servicios.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.DecimalFormat;
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

import uabc.taller.videoclubs.entidades.Ticket;
import uabc.taller.videoclubs.util.QRCode;

public class ReceiptPDFExporter {
    private final Ticket ticket;

    public ReceiptPDFExporter(Ticket ticket) {
        this.ticket = ticket;
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

        Paragraph multa = new Paragraph("Multa #" + ticket.getTicketId(), fontTitle);
        multa.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(multa);

        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("FECHA DE MULTA:", fontBodyBold));
        document.add(new Paragraph(dateFormat.format(new Date(ticket.getTicketDate().getTime())), fontBody));

        document.add(Chunk.NEWLINE);

        Paragraph renta = new Paragraph("RENTA #" + ticket.getRental().getRentalId(), fontBodyBold);
        renta.setAlignment(Paragraph.ALIGN_CENTER);
        document.add(renta);

        document.add(new Paragraph("CLIENTE:", fontBodyBold));
        document.add(new Paragraph(String.format("%d - %s", ticket.getRental().getCustomer().getCustomerId(), Arrays.stream(
                        String.format("%s %s", ticket.getRental().getCustomer().getFirstName().trim(), ticket.getRental().getCustomer().getLastName().trim()).split(" "))
                .map(nombre -> nombre.substring(0, 1).toUpperCase() + nombre.substring(1)).collect(Collectors.joining(" "))), fontBody));

        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("IMPORTE:", fontBodyBold));
        document.add(new Paragraph(new DecimalFormat("$#,###.00").format(ticket.getAmount()), fontBody));

        if (Boolean.FALSE.equals(ticket.getActive())) {
            document.add(Chunk.NEWLINE);
            document.add(Chunk.NEWLINE);

            InputStream pagado = getClass().getClassLoader()
                    .getResourceAsStream("static/assets/imgs/pagado.jpg");

            if (pagado != null) {
                Image stamp = Image.getInstance(pagado.readAllBytes());
                stamp.scaleAbsolute(112, 40);
                stamp.setAlignment(Element.ALIGN_CENTER);
                document.add(stamp);
            }
        }

        QRCode code = new QRCode();
        Optional<Image> image = code.generateBarcode(String.format("M%dR%dC%d", ticket.getTicketId(), ticket.getRental().getRentalId(), ticket.getRental().getCustomer().getCustomerId()), instance.getDirectContent());
        if (image.isPresent()) {
            image.get().scaleAbsolute(156, 41);
            image.get().setAbsolutePosition(30, document.bottom());
            document.add(image.get());
        }

        document.close();

    }

}
