<%

   session.invalidate();
   response.sendRedirect(request.getContextPath()+"/users/index.jsp");

%>
