package server.adherent;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAdherents implements Runnable
{
    private int port;
    private ServerSocket serverSocket;

    public ServerAdherents(int port)
    {
        this.port = port;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        try
        {
            serverSocket = new ServerSocket(port);
            for (; ; )
            {
                Socket s = serverSocket.accept();
                new GestionnaireAdherent(s);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    @Override
    protected void finalize() throws Throwable
    {
        if (serverSocket != null)
            serverSocket.close();
        super.finalize();
    }
}
