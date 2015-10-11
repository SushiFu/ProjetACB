package server;

import server.model.Adherent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.List;

public class GestionnaireLogin
{
    private static final String EXISTE_PAS = "Cet adherent n'existe pas ou errones";
    private static final String DEJA_CONNECTE = "Cet adherent est deja connecte";
    private static final String CONNEXION = "Vous etes connecte";

    private Socket socket;

    public GestionnaireLogin(Socket socket)
    {
        this.socket = socket;
    }

    public Adherent login()
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            for (; ; )
            {
                Adherent adh = new Adherent(br.readLine(), br.readLine());
                if (!RessourcePartage.adherents.contains(adh))
                {
                    pw.println(false);
                    pw.println(EXISTE_PAS);
                }
                else if (RessourcePartage.connected.contains(adh))
                {
                    pw.println(false);
                    pw.println(DEJA_CONNECTE);
                }
                else
                {
                    RessourcePartage.connected.add(adh);
                    pw.println(true);
                    pw.println(CONNEXION);
                    return adh;
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }
}
