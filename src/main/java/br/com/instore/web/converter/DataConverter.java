package br.com.instore.web.converter;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.converter.Converter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

@Convert(Date.class)
public class DataConverter implements Converter<Date> {

    @Override
    public Date convert(String string, Class<? extends Date> type) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(string);
        } catch (ParseException e) {
            e.printStackTrace();
            try {
                return new SimpleDateFormat("dd/MM/yyyy").parse(string);
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
