package server;

import java.net.Socket;

public class ClientManager implements Runnable
{
    private Socket socket;

    public ClientManager(Socket s)
    {
        this.socket = s;
    }

    @Override
    public void run()
    {
    }
}
