package server.model;

public class Adherent
{
    private String id;
    private String password;

    public Adherent(String id, String password)
    {
        this.id = id;
        this.password = password;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Adherent adherent = (Adherent) o;

        if (!id.equals(adherent.id)) return false;
        return !(password != null ? !password.equals(adherent.password) :
                 adherent.password != null);
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
