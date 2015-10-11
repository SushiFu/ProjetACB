package server.adherent;

import server.RessourcePartage;
import server.model.Cours;
import server.model.Utilisateur;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class GestionnairePreInscription
{
    private static final String END_MSG = "quit";
    private static final String ERROR = "La requete est invalide";
    private static final String COURS_PLEIN = "Ce COURS est complet";
    private static final String DEJA_INSCRIT = "Vous etes deja inscrit ou preinscrit dans ce COURS";
    private static final String VALIDE = "Vous etes preinscrit pour le COURS: ";

    private Socket socket;
    private Utilisateur utilisateur;

    public GestionnairePreInscription(Socket socket, Utilisateur adh)
    {
        this.socket = socket;
        this.utilisateur = adh;
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
                {
                    RessourcePartage.CONNECTED.remove(utilisateur);
                    br.close();
                    pw.close();
                    break;
                }
                Cours cours = recupererCours(res, pw);
                if (cours != null)
                {
                    boolean estAjouter = cours.ajouterPreinscription(utilisateur);
                    if (!estAjouter)
                        pw.println(DEJA_INSCRIT);
                    else
                        pw.println(VALIDE + cours.toString());
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
