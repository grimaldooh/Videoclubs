package uabc.taller.videoclubs.util;

import com.itextpdf.text.BadElementException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.Barcode128;
import com.itextpdf.text.pdf.BarcodeQRCode;
import com.itextpdf.text.pdf.PdfContentByte;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

public class QRCode {

    private final Logger logger = LoggerFactory.getLogger(QRCode.class);

    public Optional<Image> generateQRCode(String text) {
        return generateQRCode(text, 880, 880);
    }

    public Optional<Image> generateQRCode(String text, int width, int height) {
        try {
            BarcodeQRCode qrCode = new BarcodeQRCode(text, width, height, null);
            return Optional.of(qrCode.getImage());
        } catch (BadElementException e) {
            logger.error(e.getLocalizedMessage());
        }
        return Optional.empty();
    }

    public Optional<Image> generateBarcode(String text, PdfContentByte contentByte) {
        Barcode128 barcode = new Barcode128();
        barcode.setCode(text);
        Image img = barcode.createImageWithBarcode(contentByte, null, null);
        return Optional.of(img);
    }
}
