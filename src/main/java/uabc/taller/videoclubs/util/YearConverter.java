package uabc.taller.videoclubs.util;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.time.Year;

@Converter
public class YearConverter implements AttributeConverter<Year, Short> {

    @Override
    public Short convertToDatabaseColumn(Year year) {
        if (year == null) {
            return null;
        }
        return (short) year.getValue();
    }

    @Override
    public Year convertToEntityAttribute(Short isoYear) {
        if (isoYear == null) {
            return null;
        }
        return Year.of(isoYear);
    }
}
