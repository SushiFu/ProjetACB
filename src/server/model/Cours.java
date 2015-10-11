package server.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Cours
{
    private String nom;
    private int capacite;

    private List<Utilisateur> inscrits;
    private ConcurrentHashMap<Utilisateur, PreInscription> preInscriptions;

    public Cours(String nom, int capacite)
    {
        this.nom = nom;
        this.capacite = capacite;

        inscrits = Collections.synchronizedList(new ArrayList<>());
        preInscriptions = new ConcurrentHashMap<>();
    }

    public boolean estPreinscrit(Utilisateur utilisateur)
    {
        return preInscriptions.containsKey(utilisateur);
    }

    public boolean estInscrit(Utilisateur utilisateur)
    {
        return inscrits.contains(utilisateur);
    }

    public synchronized boolean ajouterPreinscription(Utilisateur utilisateur)
    {
        if (!estPreinscrit(utilisateur) && !estInscrit(utilisateur) && !estComplet())
        {
            preInscriptions.put(utilisateur, new PreInscription(utilisateur, preInscriptions));
            return true;
        }
        return false;
    }

    public synchronized void ajouterInscription(Utilisateur utilisateur)
    {
        preInscriptions.get(utilisateur).cancel();
        preInscriptions.remove(utilisateur);

        if (!inscrits.contains(utilisateur))
            inscrits.add(utilisateur);
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

    public String toStringPreInscrit()
    {
        return nom + " " + preInscriptions.size() + " preinscrits";
    }

    public String preinscritsNoms()
    {
        StringBuilder sb = new StringBuilder();
        for (Utilisateur utilisateur : preInscriptions.keySet())
            sb.append(utilisateur.toString()).append(" / ");
        return sb.toString();
    }
}
