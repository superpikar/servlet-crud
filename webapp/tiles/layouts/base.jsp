<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<html>
  <head>
  	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title><tiles:getAsString name="title"/></title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/bulma/0.3.1/css/bulma.min.css"></link>
  </head>
  <body>
     <header id="header">
        <tiles:insertAttribute name="header" />
     </header>
     <section id="sidemenu">
     	<tiles:insertAttribute name="sidemenu" />
     </section>
     <section id="content">
     	<tiles:insertAttribute name="content" />
     </section>
     <footer id="footer">
     	<tiles:insertAttribute name="footer" />
     </footer>
  </body>
</html>