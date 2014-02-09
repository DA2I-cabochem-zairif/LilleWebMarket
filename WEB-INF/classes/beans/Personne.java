package beans;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;
import java.sql.*;
import java.util.*;

public class Personne
{
    private int iduser, cash;
    private String nom, prenom, login, mdp, droit;
    private boolean admin;
    protected Connection con = null;
    
    public Personne()
    {
	this.initialize();
    }
    
    public void initialize()
    {
	try
	{
	    Class.forName("org.postgresql.Driver");
	
	    String url = "jdbc:postgresql://localhost/da2i";
	    String user = "fouad";
	    String mdp = "moi";
	    this.con = DriverManager.getConnection(url, user, mdp);
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
    
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
    
    public String getDroit()
    {
	return this.droit;
    }
    
    public int getCash()
    {
	return this.cash;
    }
    
    public boolean estAdmin()
    {
	return this.admin;
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
    
    public void setDroit(String droit)
    {
	this.droit = droit;
    }
    
    public void setEstAdmin(boolean admin)
    {
	this.admin = admin;
    }
    
    public void rechUser(String log, HttpSession session)
    {
	try
	{
	    String query = "select * from utilisateur where login = ? ";
	    query += ";";
	    PreparedStatement ps = this.con.prepareStatement(query);
	    ps.setString(1, log);
	    ResultSet rs = ps.executeQuery();
	    rs.next();
	    int iduser = rs.getInt("iduser");
	    String nom = rs.getString("nom");
	    String prenom = rs.getString("prenom");
	    String login = rs.getString("login");
	    String mdp = rs.getString("mdp");
	    int cash = rs.getInt("cash");
	    String droit = rs.getString("droit");
	    session.setAttribute("iduser", iduser);
	    session.setAttribute("nom", nom);
	    session.setAttribute("prenom", prenom);
	    session.setAttribute("login", login);
	    session.setAttribute("mdp", mdp);
	    session.setAttribute("cash", cash);
	    session.setAttribute("droit", droit.equals("admin"));
	    con.close();
	}
	catch (Exception e)
	{
	    e.printStackTrace();
	}
    }
}