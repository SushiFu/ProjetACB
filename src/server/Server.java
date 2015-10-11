package server;

import server.adherent.ServerAdherents;
import server.admin.ServerAdmins;

public class Server
{
    public static void main(String[] args)
    {
        new ServerAdherents(3000);
        new ServerAdmins(4000);
    }
}
