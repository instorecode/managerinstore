package br.com.instore.web.component.application;

import br.com.caelum.vraptor.interceptor.multipart.DefaultMultipartConfig;
import br.com.caelum.vraptor.ioc.ApplicationScoped;
import br.com.caelum.vraptor.ioc.Component;

@Component
@ApplicationScoped
public class CustomMultipartConfig extends DefaultMultipartConfig {

    public long getFileSizeLimit() {
        return 50 * 1024 * 1024; // 50MB
    }

    public long getSizeLimit() {
        return 50 * 1024 * 1024; // 50MB
    }
}
