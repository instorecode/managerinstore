package br.com.instore.web.component.application;

import br.com.instore.core.orm.SessionFactoryUtils;
import javax.enterprise.context.SessionScoped;
import org.hibernate.ResourceClosedException;
import org.hibernate.Session;
import org.hibernate.TransactionException;

public class Initialize implements java.io.Serializable {

    private Session session;

    public Session getSession() {
        verifySession();
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public void verifySession() {
        try {
            if (session == null) {
                session = SessionFactoryUtils.getInstance().session();
                session.beginTransaction();
            } else {
                if (!session.isOpen() || !session.isConnected()) {
                    session = session.getSessionFactory().openSession();
                    session.beginTransaction();
                }
            }
        } catch (TransactionException e) {
            if (null != session && session.isOpen()) {
                session.clear();
                session.close();
            }
            verifySession();
        } catch (ResourceClosedException e) {
            if (null != session && session.isOpen()) {
                session.clear();
                session.close();
            }
            verifySession();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}