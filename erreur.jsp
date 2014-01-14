<!DOCTYPE HTML>
<html>
    <head>
	<title>Page d'erreur</title>
        <%@ page
               contentType="text/html; charset=ISO-8859-15" 
	       isErrorPage="true" %>
	<link rel="stylesheet" href="css/main.css" type="text/css">
    </head>
<body>

<article>
    <div class="wrap">
        <h1> Page de gestion d'erreur</h1>
        <p> Un probleme de type 
        <%
	    String m = request.getParameter("message");
	    if (m!=null) out.print(m);
	    if (exception!=null) out.print(exception.getMessage());
        %>
        est survenu.</p>
        <p><a href="#" alt="retour a l'accueil">Retour</a></p>
    </div>
    
</article>
</body>
</html>
