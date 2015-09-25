package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Server implements Runnable
{
    private int port;
    private ServerSocket serverSocket;

    public static void main(String[] args)
    {
        new Server(3000);
    }

    public Server(int port)
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
            while (true)
            {
                Socket s = serverSocket.accept();

            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}
