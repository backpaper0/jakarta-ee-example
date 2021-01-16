<%@ page contentType="text/plain" session="false" %><%--
--%><%
((java.util.List<String>)request.getAttribute("names")).add("demo.jsp");
%><%--
--%><%=((java.util.List<String>)request.getAttribute("names")).stream().collect(java.util.stream.Collectors.joining(" ")) %>
