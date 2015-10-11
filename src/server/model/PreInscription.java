package server.model;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.ConcurrentHashMap;

public class PreInscription extends TimerTask
{
    private static final long DAY = 1000L * 60L * 60L * 24L;

    private Adherent adherent;
    private ConcurrentHashMap<Adherent, PreInscription> preInscriptions;

    public PreInscription(Adherent adherent,
                          ConcurrentHashMap<Adherent, PreInscription> preInscriptions)
    {
        this.adherent = adherent;
        this.preInscriptions = preInscriptions;
        new Timer().schedule(this, DAY);
    }

    @Override
    public void run()
    {
        preInscriptions.remove(adherent);
        this.cancel();
    }
}
