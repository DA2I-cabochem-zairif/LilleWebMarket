<%@page pageEncoding="utf-8" %>
<% session.setAttribute("page","Erreur"); %>
<jsp:include page="partial/header.jsp"/>
<%@ page contentType="text/html; charset=ISO-8859-15" isErrorPage="true" %>

<article>
    <div class="wrap">
        <h1>Un problème est survenue</h1>
        <p> Un probleme de type 
        <%
	    String m = request.getParameter("message");
	    if (m!=null) out.print(m);
	    if (exception!=null) out.print(exception.getMessage());
        %>
        est survenu.</p>
        <p><a href="<%= request.getContextPath() %>/users/index.jsp" alt="retour a l'accueil">Retour</a></p>
    </div>
    
</article>

<jsp:include page="partial/footer.jsp" />
