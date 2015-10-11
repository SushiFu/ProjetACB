package server.admin;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerAdmins implements Runnable
{
    private int port;
    private ServerSocket serverSocket;

    public ServerAdmins(int port)
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
                new GestionnaireAdmin(s);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
