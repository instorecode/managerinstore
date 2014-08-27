package br.com.instore.web.servlet;

import br.com.instore.core.orm.DataValidatorException;
import br.com.instore.core.orm.Each;
import br.com.instore.core.orm.RepositoryViewer;
import br.com.instore.core.orm.bean.UsuarioBean;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet(name = "lancamentoServlet", urlPatterns = "/lancamento/efetuar-baixa/task")
public class LancamentoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            final RepositoryViewer rv = new RepositoryViewer();
            rv.setUsuario(new UsuarioBean(1));
            
            
            final String query =    "select concat('update lancamento_cnpj set saldo_disponivel = saldo_disponivel - (select sum(valor) from lancamento where debito = 1 and data_fechamento is not null and mes = date(\\'2014-08-27\\') and lancamento_cnpj = ',id,') where lancamento_cnpj.id = ', id) as debito ,\n" +
                                    "	   concat('update lancamento_cnpj set saldo_disponivel = saldo_disponivel + (select sum(valor) from lancamento where credito = 1 and data_fechamento is not null and mes = date(\\'2014-08-27\\') and lancamento_cnpj = ',id,') where lancamento_cnpj.id = ', id) as credito\n" +
                                    "from lancamento_cnpj;";

            rv.query(query).executeSQL(new Each() {
                String debito;
                String credito;

                @Override
                public void each() {
                    try {
                        System.out.println("SCRIPT");
                        System.out.println(query);
                        System.out.println("----------------------------------------------------------");
                        System.out.println("DEBITO: " + debito);
                        System.out.println("CREDITO: " + credito);
                        System.out.println("----------------------------------------------------------");
                        System.out.println("");
                        
                        rv.query(debito).executeSQLCommand();
                        rv.query(credito).executeSQLCommand();
                    } catch (DataValidatorException e) {
                        e.printStackTrace();
                    }
                }
            });
            
            rv.finalize();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
