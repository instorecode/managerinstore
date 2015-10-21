package br.com.instore.web.servlet;

import br.com.caelum.vraptor.interceptor.download.InputStreamDownload;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet( name = "initializeThreads" ,loadOnStartup = 1)
public class Callgc extends HttpServlet {

    private String css;
    private String js;
    private InputStreamDownload cssFileDownload;
    private InputStreamDownload jsFileDownload;
    
    @Override
    public void init() throws ServletException {
        Timer timer = new Timer("CALL GC ");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("CALL GC ".concat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));
                System.gc();
            }
        }, 60000l, (60000*20));
    }
}
