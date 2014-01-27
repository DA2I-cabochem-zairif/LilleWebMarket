package bean;

public class Personne
{
    private int iduser, cash;
    private String nom, prenom, login, mdp;
    
    public Personne(){}
    
    public int getIdUser()
    {
	return this.iduser;
    }
    
    public String getNom()
    {
	return this.nom;
    }
    
    public String getPrenom()
    {
	return this.prenom;
    }
    
    public String getLogin()
    {
	return this.login;
    }
    
    public String getMdp()
    {
	return this.mdp;
    }
    
    public int getCash()
    {
	return this.cash;
    }
    
    public void setIdUser(int iduser)
    {
	this.iduser = iduser;
    }
    
    public void setNom(String nom)
    {
	this.nom = nom;
    }
    
    public void setPrenom(String prenom)
    {
	this.prenom = prenom;
    }
    
    public void setLogin(String login)
    {
	this.login = login;
    }
    
    public void setMdp(String mdp)
    {
	this.mdp = mdp;
    }
    
    public void setCash(int cash)
    {
	this.cash = cash;
    }
    
    public String getHtml()
    {
	String html = "<tr><td>"+this.iduser+" </td><td>"+this.nom+" </td><td>"+this.prenom+" </td><td>"+this.login+" </td><td>"+this.mdp+" </td><td>"+this.cash+" </td></tr>";
	
	return html;
    }
}