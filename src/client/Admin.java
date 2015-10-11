package client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Admin implements Runnable
{
    private static final String END_MSG = "quit";

    private int port;

    public static void main(String[] args)
    {
        new Admin(4000);
    }

    public Admin(int port)
    {
        this.port = port;
        new Thread(this).start();
    }

    @Override
    public void run()
    {
        try
        {
            Socket socket = new Socket("localhost", port);
            BufferedReader br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter pw = new PrintWriter(socket.getOutputStream(), true);
            Scanner sc = new Scanner(System.in);
            login(sc, br, pw);
            gerer(sc, br, pw);
            sc.close();
            socket.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    private void login(Scanner sc, BufferedReader br, PrintWriter pw) throws IOException
    {
        boolean connected;
        do
        {
            System.out.print("Entrez l'id de l'utlisateur: ");
            String id = sc.nextLine();
            System.out.print("Entrez le mot de passe de l'utilisateur: ");
            String pass = sc.nextLine();
            pw.println(id);
            pw.println(pass);
            connected = Boolean.parseBoolean(br.readLine());
            System.out.println(br.readLine());
        } while (!connected);
    }

    private void gerer(Scanner sc, BufferedReader br, PrintWriter pw) throws IOException
    {
        for (; ;)
        {
            System.out.println(br.readLine());
            System.out.print("Entrez le nom du COURS ou quit: ");
            String res = sc.nextLine();
            pw.println(res);

            if (res.equals(END_MSG))
                break;

            boolean correct = Boolean.parseBoolean(br.readLine());
            System.out.println(br.readLine());
            if (correct)
            {
                System.out.print("Entrez le nom de l'utilisateur a valide: ");
                res = sc.nextLine();
                pw.println(res);
                System.out.println(br.readLine());
            }
        }
    }
}
