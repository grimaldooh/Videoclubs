package uabc.taller.videoclubs.servicios.pdf;

import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

import uabc.taller.videoclubs.dto.FilmDetails;
import uabc.taller.videoclubs.util.QRCode;

public class FilmPDFExporter {
	private final FilmDetails film;
	
	public FilmPDFExporter(FilmDetails film) {
		this.film = film;
	}
	
	public void export(HttpServletResponse response) throws DocumentException, IOException {
		Document document = new Document(PageSize.LETTER, 50, 50, 50, 50);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		
		BaseColor negro = BaseColor.BLACK;
        BaseColor colorSecundario = new BaseColor(0, 165, 85); // Color: #00a555

        // Agregar título
        Font titleFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 25, colorSecundario);
        Paragraph title = new Paragraph(film.getTitle(), titleFont);
        title.setAlignment(Element.ALIGN_LEFT);
        title.setSpacingAfter(20f);
        document.add(title);

        Font boldFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, negro);
        Font normalFont = FontFactory.getFont(FontFactory.HELVETICA, 12, negro);

        document.add(twoFontsOneLine("Año de Estreno: ", boldFont, film.getReleaseYear().toString(),
                normalFont));

        document.add(Chunk.NEWLINE);

        document.add(twoFontsOneLine("Duración: ", boldFont, String.format("%d min.", film.getLength()),
                normalFont));

        document.add(Chunk.NEWLINE);

        document.add(twoFontsOneLine("Idioma: ", boldFont, film.getLanguage().getName(), normalFont));

        document.add(Chunk.NEWLINE);

        document.add(twoFontsOneLine("Duración de la Renta: ", boldFont,
                String.format("%d días", film.getRentalDuration()), normalFont));

        document.add(Chunk.NEWLINE);

        document.add(twoFontsOneLine("Clasificación: ", boldFont, film.getRating().getMpaa(), normalFont));

        document.add(Chunk.NEWLINE);

        // Agregar lista de categorías
        document.add(new Paragraph("Categorías:", boldFont));
        document.add(new Paragraph(film.getFilmCategories().stream().map(i -> i.getCategory().getName())
                .collect(Collectors.joining(", ")), normalFont));

        document.add(Chunk.NEWLINE);

        document.add(new Paragraph("Descripción:", boldFont));
        document.add(new Paragraph(film.getDescription(), normalFont));

        document.add(Chunk.NEWLINE);

        // Agregar lista de actores
        document.add(new Paragraph("Reparto:", boldFont));
        document.add(new Paragraph(film.getFilmActors().stream()
                .map(actor -> actor.getActor().getName().toLowerCase().split(" "))
                .map(nombres -> Arrays.stream(nombres)
                        .map(nombre -> nombre.substring(0, 1).toUpperCase() + nombre.substring(1))
                        .collect(Collectors.joining(" ")))
                .collect(Collectors.joining(", ")), normalFont));

        document.add(Chunk.NEWLINE);

        // Agregar lista de caracteristicas especiales
        document.add(new Paragraph("Características Especiales:", boldFont));
        document.add(new Paragraph(String.join(", ", film.getSpecialFeatures()), normalFont));

        InputStream template = getClass().getClassLoader()
                .getResourceAsStream("static/assets/imgs/sakila-logo1.jpg");

        if (template != null) {
            Image logo = Image.getInstance(template.readAllBytes());
            logo.scaleAbsolute(100, 100);
            logo.setAbsolutePosition(document.right() - 60, document.bottom() - 20);
            document.add(logo);
        }

        QRCode code = new QRCode();
        Optional<Image> qr = code.generateQRCode("https://forms.gle/3efSHUAZFVE5Fmgh8");
        if (qr.isPresent()) {
            qr.get().scaleAbsolute(100, 100);
            qr.get().setAbsolutePosition(document.left() - 20, document.bottom() - 20);
            document.add(qr.get());
        }
		
		document.close();
	}
	
	private Paragraph twoFontsOneLine(String text1, Font font1, String text2, Font font2) {
        Paragraph paragraph = new Paragraph();
        paragraph.add(new Chunk(text1, font1));
        paragraph.add(new Chunk(text2, font2));
        return paragraph;
    }
}
