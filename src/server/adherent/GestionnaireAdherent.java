package server.adherent;

import server.GestionnaireLogin;
import server.model.Adherent;

import java.net.Socket;

public class GestionnaireAdherent implements Runnable
{

    private Socket socket;

    public GestionnaireAdherent(Socket s)
    {
        this.socket = s;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        Adherent adh = new GestionnaireLogin(socket).login();
        if (adh != null)
            new GestionnairePreInscription(socket, adh).inscrire();
    }

    @Override
    protected void finalize() throws Throwable
    {
        if (socket != null)
            socket.close();
        super.finalize();
    }
}
