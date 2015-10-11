package server;

import server.model.Utilisateur;
import server.model.Cours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RessourcePartage
{
    public static final List<Utilisateur> UTILISATEURS = Collections.synchronizedList(new ArrayList<>());
    public static final List<Cours> COURS = Collections.synchronizedList(new ArrayList<>());
    public static final List<Utilisateur> CONNECTED = Collections.synchronizedList(new ArrayList<>());

    static
    {
        UTILISATEURS.add(new Utilisateur("test1", "1337"));
        UTILISATEURS.add(new Utilisateur("test2", "4242"));
        UTILISATEURS.add(new Utilisateur("test3", "42"));

        COURS.add(new Cours("Math", 5));
        COURS.add(new Cours("Progra", 2));
        COURS.add(new Cours("Web", 3));
    }

    public static Utilisateur trouverAdherent(String id)
    {
        for (Utilisateur utilisateur : UTILISATEURS)
            if (utilisateur.equals(id))
                return utilisateur;
        return null;
    }

    public static Cours trouverCours(String nom)
    {
        for (Cours cour : COURS)
            if (cour.equals(nom))
                return cour;
        return null;
    }
}
