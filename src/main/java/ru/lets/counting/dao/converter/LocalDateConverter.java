package ru.lets.counting.dao.converter;

import java.sql.Date;
import java.time.LocalDate;
import org.jooq.Converter;

/**
 *
 * @author Евгений
 */
public class LocalDateConverter implements Converter<Date, LocalDate>{

    @Override
    public LocalDate from(Date databaseObject) {
        return databaseObject.toLocalDate();
    }

    @Override
    public Date to(LocalDate userObject) {
        return Date.valueOf(userObject);
    }

    @Override
    public Class<Date> fromType() {
        return Date.class;
    }

    @Override
    public Class<LocalDate> toType() {
        return LocalDate.class;
    }
    
}
