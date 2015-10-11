package server.admin;

import server.RessourcePartage;
import server.model.Utilisateur;
import server.model.Cours;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GestionnaireInscription
{
    private static final String END_MSG = "quit";
    private static final String COURS_INTROUVABLE = "Ce COURS est introuvable";
    private static final String INEXISTANT = "Cet id n'existe pas";
    private static final String VALIDE = "L'inscription a ete valide";

    private Socket socket;
    private Utilisateur utilisateur;

    public GestionnaireInscription(Socket socket, Utilisateur utilisateur)
    {
        this.socket = socket;
        this.utilisateur = utilisateur;
    }

    public void gererInscriptions()
    {
        try
        {
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);

            for (; ; )
            {
                envoyerCours(pw);
                String res = br.readLine();

                if (res.equals(END_MSG))
                {
                    RessourcePartage.CONNECTED.remove(utilisateur);
                    br.close();
                    pw.close();
                    break;
                }

                Cours cours = RessourcePartage.trouverCours(res);
                if (cours == null)
                {
                    pw.println(false);
                    pw.println(COURS_INTROUVABLE);
                }
                else
                {
                    pw.println(true);
                    pw.println(cours.preinscritsNoms());
                    String id = br.readLine();
                    gererInscription(RessourcePartage.trouverAdherent(id), cours, pw);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void envoyerCours(PrintWriter pw)
    {
        StringBuilder sb = new StringBuilder();
        for (Cours cours : RessourcePartage.COURS)
            if (cours.preinscriptionsAttente())
                sb.append(cours.toStringPreInscrit()).append(" / ");
        pw.println(sb.toString());
    }

    private void gererInscription(Utilisateur utilisateur, Cours cours, PrintWriter pw)
    {
        if (utilisateur == null)
            pw.println(INEXISTANT);
        else
        {
            cours.ajouterInscription(utilisateur);
            pw.println(VALIDE);
        }
    }
}
