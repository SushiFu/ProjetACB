package server.admin;

import server.GestionnaireLogin;
import server.model.Adherent;

import java.net.Socket;

public class GestionnaireAdmin implements Runnable
{
    private Socket socket;

    public GestionnaireAdmin(Socket s)
    {
        this.socket = s;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        Adherent adh = new GestionnaireLogin(socket).login();
        if (adh != null)
            new GestionnaireInscription(socket).gererInscriptions();
    }
}
