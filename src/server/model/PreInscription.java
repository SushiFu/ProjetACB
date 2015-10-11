package server.model;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class PreInscription extends TimerTask
{
    private static final long DAY = 1000L * 60L * 60L * 24L;

    private Utilisateur utilisateur;
    private ConcurrentHashMap<Utilisateur, PreInscription> preInscriptions;

    public PreInscription(Utilisateur utilisateur,
                          ConcurrentHashMap<Utilisateur, PreInscription> preInscriptions)
    {
        this.utilisateur = utilisateur;
        this.preInscriptions = preInscriptions;
        new Timer().schedule(this, DAY);
    }

    @Override
    public void run()
    {
        preInscriptions.remove(utilisateur);
        this.cancel();
    }
}
