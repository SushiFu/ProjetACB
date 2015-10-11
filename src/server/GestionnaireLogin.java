package server;

import server.model.Utilisateur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

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

    public Utilisateur login()
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            for (; ; )
            {
                Utilisateur adh = new Utilisateur(br.readLine(), br.readLine());
                synchronized (RessourcePartage.CONNECTED)
                {
                    if (!RessourcePartage.UTILISATEURS.contains(adh))
                    {
                        pw.println(false);
                        pw.println(EXISTE_PAS);
                    }
                    else if (RessourcePartage.CONNECTED.contains(adh))
                    {
                        pw.println(false);
                        pw.println(DEJA_CONNECTE);
                    }
                    else
                    {
                        RessourcePartage.CONNECTED.add(adh);
                        pw.println(true);
                        pw.println(CONNEXION);
                        return adh;
                    }
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
