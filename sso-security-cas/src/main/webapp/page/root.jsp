<%@page contentType="java" pageEncoding="utf-8"%>
<%
response.setCharacterEncoding("utf-8");
out.println("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\" />");
out.println("<meta charset=\"utf-8\" />");
out.println("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0\" />");
out.println("<meta name=\"renderer\" content=\"webkit\">");
out.println("<meta http-equiv=\"Pragma\" content=\"no-cache\">");
out.println("<meta http-equiv=\"Cache-Control\" content=\"no-siteapp,no-cache,no-store\"/>");
out.println("<meta http-equiv=\"Expires\" content=\"0\">");
if (request.getServerPort() == 80){
	out.println("<base href= '" + request.getScheme()+"://" + request.getServerName() + request.getContextPath() + "/' />");
}else{
	out.println("<base href= '" + request.getScheme()+"://" + request.getServerName() + ":"+request.getServerPort() + request.getContextPath() + "/' />");
}

out.flush();
%>
<%
	String version ="EUMP1.0.0.001";
	String ctxPath = request.getContextPath();
	final String  BIZCTX_PATH=ctxPath;
%>
<script>
	window.ctxPaths = "<%= ctxPath%>";
	window.BIZCTX_PATH="<%= BIZCTX_PATH%>";
	window.DOMAIN="<%=application.getInitParameter("domain")%>";
</script>
<link rel="shortcut icon" type="image/x-icon" href="<%=ctxPath%>/pages/image/favicon.ico"/>