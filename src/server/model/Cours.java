package server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Cours
{
    private String nom;
    private int capacite;

    private List<Adherent> inscrits;
    private ConcurrentHashMap<Adherent, PreInscription> preInscriptions;

    public Cours(String nom, int capacite)
    {
        this.nom = nom;
        this.capacite = capacite;

        inscrits = Collections.synchronizedList(new ArrayList<>());
        preInscriptions = new ConcurrentHashMap<>();
    }

    public boolean estPreinscrit(Adherent adherent)
    {
        return preInscriptions.containsKey(adherent);
    }

    public boolean estInscrit(Adherent adherent)
    {
        return inscrits.contains(adherent);
    }

    public boolean ajouterPreinscription(Adherent adherent)
    {
        if (!estPreinscrit(adherent) && !estInscrit(adherent) && !estComplet())
        {
            preInscriptions.put(adherent, new PreInscription(adherent, preInscriptions));
            return true;
        }
        return false;
    }

    public void ajouterInscription(Adherent adherent)
    {
        preInscriptions.get(adherent).cancel();
        preInscriptions.remove(adherent);

        if (!inscrits.contains(adherent))
            inscrits.add(adherent);
    }

    public boolean estComplet()
    {
        return capacite <= inscrits.size() + preInscriptions.size();
    }

    public boolean preinscriptionsAttente()
    {
        return preInscriptions.size() > 0;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Cours cours = (Cours) o;
        if (capacite != cours.capacite) return false;
        return !(nom != null ? !nom.equals(cours.nom) : cours.nom != null);
    }

    public boolean equals(String nom)
    {
        return this.nom.equals(nom);
    }

    @Override
    public String toString()
    {
        return nom + " " + (inscrits.size() + preInscriptions.size()) + "/" + capacite + " " +
               "inscrits";
    }

    public String preinscritsName()
    {
        StringBuilder sb = new StringBuilder();
        for (Adherent adherent : preInscriptions.keySet())
            sb.append(adherent.toString()).append(" / ");
        return sb.toString();
    }
}
