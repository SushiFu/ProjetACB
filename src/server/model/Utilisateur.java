package server.model;

public class Utilisateur
{
    private String id;
    private String password;

    public Utilisateur(String id, String password)
    {
        this.id = id;
        this.password = password;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Utilisateur utilisateur = (Utilisateur) o;

        if (!id.equals(utilisateur.id)) return false;
        return !(password != null ? !password.equals(utilisateur.password) :
                 utilisateur.password != null);
    }

    @Override
    public int hashCode()
    {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (password != null ? password.hashCode() : 0);
        return result;
    }

    public boolean equals(String id)
    {
        return this.id.equals(id);
    }

    @Override
    public String toString()
    {
        return id;
    }
}
