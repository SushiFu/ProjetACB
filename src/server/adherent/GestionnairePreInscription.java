package server.adherent;

import server.RessourcePartage;
import server.model.Adherent;
import server.model.Cours;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GestionnairePreInscription
{
    private static final String END_MSG = "quit";
    private static final String ERROR = "La requete est invalide";
    private static final String COURS_PLEIN = "Ce cours est complet";
    private static final String DEJA_INSCRIT = "Vous etes deja inscrit ou preinscrit dans ce cours";
    private static final String VALIDE = "Vous etes preinscrit pour le cours: ";

    private Socket socket;
    private Adherent adherent;

    public GestionnairePreInscription(Socket socket, Adherent adh)
    {
        this.socket = socket;
        this.adherent = adh;
    }

    public void inscrire()
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
                    break;
                Cours cours = recupererCours(res, pw);
                if (cours != null)
                {
                    boolean estAjouter = cours.ajouterPreinscription(adherent);
                    if (!estAjouter)
                        pw.println(DEJA_INSCRIT);
                    else
                        pw.println(VALIDE + cours.toString());
                }
            }

            br.close();
            pw.close();
            RessourcePartage.connected.remove(adherent);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void envoyerCours(PrintWriter pw)
    {
        StringBuilder sb = new StringBuilder();
        for (Cours cours : RessourcePartage.cours)
            if (!cours.estComplet())
                sb.append(cours.toString()).append(" / ");
        pw.println(sb.toString());
    }

    private Cours recupererCours(String cours, PrintWriter pw)
    {
        try
        {
            Cours c = RessourcePartage.trouverCours(cours);
            if (c == null)
            {
                pw.println(ERROR);
                return null;
            }
            if (c.estComplet())
            {
                pw.println(COURS_PLEIN);
                return null;
            }
            return c;
        }
        catch (NumberFormatException e)
        {
            e.printStackTrace();
            pw.println(ERROR);
        }
        return null;
    }
}
