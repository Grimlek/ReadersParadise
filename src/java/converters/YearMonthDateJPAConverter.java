/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package converters;

import java.time.YearMonth;
import java.time.ZoneId;
import java.util.Date;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/*
*
* Converter for java.time.YearMonth as JPA 2.1 was released before Java 8. Some
* JPA providers already have this implementation for the new Java Time classes. 
* Eclipselink doesn't support the conversion yet. Using YearMonth as a type will 
* generate a binary byte stream and try to store that within the database. That
* will work but that is unreadable and the field type has to be changed to varchar(100)
*
*/
@Converter(autoApply = true)
public class YearMonthDateJPAConverter implements AttributeConverter<YearMonth, Date> {

    @Override
    public Date convertToDatabaseColumn(YearMonth entityValue) {
        return Date.from(entityValue.atDay(1).atStartOfDay(ZoneId.systemDefault()).toInstant());
    }

    @Override
    public YearMonth convertToEntityAttribute(Date dbValue) {
        return YearMonth.from(dbValue.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
    }
}
