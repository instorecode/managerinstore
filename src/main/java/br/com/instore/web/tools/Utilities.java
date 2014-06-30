package br.com.instore.web.tools;

import br.com.instore.core.orm.bean.HistoricoUsuarioBean;
import br.com.instore.web.component.session.SessionRepository;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import org.apache.commons.lang.StringUtils;

public class Utilities {
    public static void main(String[] args) {
        System.out.println("ola mundo");
    }
    
    public static String leftPad(Integer s ) {
        return StringUtils.leftPad(s.toString(), 11, "0");
    }
    
    public static String leftPad(String s , String add, int size) {
        return StringUtils.leftPad(s, size, add);
    }
    
    public static String md5(String t) throws NoSuchAlgorithmException {

        MessageDigest m = MessageDigest.getInstance("MD5");
        m.reset();
        m.update(t.getBytes());
        byte[] digest = m.digest();

        BigInteger bigInt = new BigInteger(1, digest);
        String hashtext = bigInt.toString(16);

        while (hashtext.length() < 32) {
            hashtext = "0" + hashtext;
        }

        return hashtext;
    }
    
    public static void historicoUsuarioLogin(SessionRepository repository) throws NoSuchAlgorithmException {
        HistoricoUsuarioBean historico = new HistoricoUsuarioBean();
        historico.setLogin(new Date());
        historico.setUsuario(repository.getUsuario());
        repository.save(historico);
        repository.finalize();
    }
    
    public static void historicoUsuarioLogOut(SessionRepository repository) throws NoSuchAlgorithmException {
        HistoricoUsuarioBean historico = new HistoricoUsuarioBean();
        historico.setLogout(new Date());
        historico.setUsuario(repository.getUsuario());
        repository.save(historico);
        repository.finalize();
    }
}
