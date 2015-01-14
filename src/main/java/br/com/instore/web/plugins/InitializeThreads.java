package br.com.instore.web.plugins;

import br.com.caelum.vraptor.observer.download.InputStreamDownload;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;

@WebServlet( name = "initializeThreads" ,loadOnStartup = 1)
public class InitializeThreads extends HttpServlet {

    private String css;
    private String js;
    private InputStreamDownload cssFileDownload;
    private InputStreamDownload jsFileDownload;
    
    @Override
    public void init() throws ServletException {
        Timer timer = new Timer("chamar-gc-periodicamente");
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                System.out.println("aqui");
                System.gc();
            }
        }, 60000l, (60000*20));
    }
}
