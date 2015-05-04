package br.com.instore.web.converter;

import br.com.caelum.vraptor.Convert;
import br.com.caelum.vraptor.Converter;
import br.com.caelum.vraptor.ioc.RequestScoped;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

@Convert(Date.class)
@RequestScoped
public class DataConverter implements Converter<Date> {
    @Override
    public Date convert(String string, Class<? extends Date> type, ResourceBundle rb) {
        try {
            return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(string);
        } catch (ParseException e) {
            try {
                return new SimpleDateFormat("dd/MM/yyyy").parse(string);
            } catch (ParseException e2) {
                e2.printStackTrace();
            }
        }
        return null;
    }
}
