package br.com.instore.web.component.application;

import br.com.caelum.vraptor.observer.upload.DefaultMultipartConfig;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Specializes;


@Specializes
@ApplicationScoped
public class CustomMultipartConfig extends DefaultMultipartConfig {

    public long getFileSizeLimit() {
        return 50 * 1024 * 1024; // 50MB
    }
    
    public long getSizeLimit() {
        return 50 * 1024 * 1024; // 50MB
    }

}