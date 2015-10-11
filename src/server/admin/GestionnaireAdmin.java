package server.admin;

import server.GestionnaireLogin;
import server.model.Utilisateur;

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
        Utilisateur utilisateur = new GestionnaireLogin(socket).login();
        if (utilisateur != null)
            new GestionnaireInscription(socket, utilisateur).gererInscriptions();
    }
}
