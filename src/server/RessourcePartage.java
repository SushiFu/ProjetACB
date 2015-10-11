package server;

import server.model.Adherent;
import server.model.Cours;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RessourcePartage
{
    public static final List<Adherent> adherents = Collections.synchronizedList(new ArrayList<>());
    public static final List<Cours> cours = Collections.synchronizedList(new ArrayList<>());
    public static final List<Adherent> connected = Collections.synchronizedList(new ArrayList<>());

    static
    {
        adherents.add(new Adherent("test1", "1337"));
        adherents.add(new Adherent("test2", "4242"));
        adherents.add(new Adherent("test3", "42"));

        cours.add(new Cours("Math", 5));
        cours.add(new Cours("Progra", 2));
        cours.add(new Cours("Web", 3));
    }

    public static Adherent trouverAdherent(String id)
    {
        for (Adherent adherent : adherents)
            if (adherent.equals(id))
                return adherent;
        return null;
    }

    public static Cours trouverCours(String nom)
    {
        for (Cours cour : cours)
            if (cour.equals(nom))
                return cour;
        return null;
    }
}
