package br.com.instore.web.event;

import br.com.caelum.vraptor.events.RequestStarted;
import javax.enterprise.event.Observes;


public class ObserverLoadMenu {
    public void load(@Observes RequestStarted requestStarted) {
        System.out.println("OLA MUNDO ESTOU VIVO");
        System.out.println("OLA MUNDO ESTOU VIVO" + requestStarted.getRequest().getMethod());
    }
}
