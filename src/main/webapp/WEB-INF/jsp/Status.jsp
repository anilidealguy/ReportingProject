<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page import = "java.io.*,java.util.*" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Status</title>
		<style>
			table,td, tr, th{
				border: 2px solid;
			}
			
			.details{
				width:100px;
				text-align:center;
				text-color:white;
			}
			th,td{
				padding:5px;
			}
			table{
				border-collapse: separate;
				border-spacing: 10px 5px;
			}
		</style>
</head>
<body>
		<%-- <%
		response.setIntHeader("Refresh", 5);
		Calendar calendar = new GregorianCalendar();
        String am_pm;
        
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);
        
        if(calendar.get(Calendar.AM_PM) == 0)
           am_pm = "AM";
        else
           am_pm = "PM";
        String CT = hour+":"+ minute +":"+ second +" "+ am_pm;
        out.println("Crrent Time: " + CT + "\n");
		%> --%>
		<center>
		<input type="button" onclick="location.href='<%=request.getContextPath() %>/index.html'" value="Home"/>
		
		<table>
			<tr>
					<th>Overall Status</th>
					<th>Detailed Status
			</tr>
			<c:forEach var="report" items="${toRun}" varStatus="status">
				<tr>
				<td>
					${report.name}
				</td>
				<td>
					<table>
						<tr>
						<c:forEach items="${report.steps}" var="step" varStatus="counter">
							
							<c:if test="${step.active == 'false'}">
								<td class="details" style="background-color:grey">
									<c:forEach items="${actionTypes}" var="entry">
										<c:if test="${step.actionType == entry.key}">
											${entry.value}
										</c:if>
									</c:forEach>
								</td>
							</c:if>
							<c:if test="${step.active == 'true'}">
								<c:if test="${step.status == 0}">
									<td class="details" style="background-color:yellow">
											<c:forEach items="${actionTypes}" var="entry">
												<c:if test="${step.actionType == entry.key}">
													${entry.value}
												</c:if>
											</c:forEach>
									</td>
								</c:if>
								
								<c:if test="${step.status == 1}">
									<td class="details" style="background-color:green">
										<c:forEach items="${actionTypes}" var="entry">
												<c:if test="${step.actionType == entry.key}">
													${entry.value}
												</c:if>
										</c:forEach>
									</td>
								</c:if>
								
								<c:if test="${step.status == 10}">
									<td class="details" style="background-color:blue">
										<c:forEach items="${actionTypes}" var="entry">
											<c:if test="${step.actionType == entry.key}">
												${entry.value}
											</c:if>
										</c:forEach>
									</td>
								</c:if>
							</c:if>
						</c:forEach>
						</tr>
					</table>
				</td>
			</tr>
			</c:forEach>
			
		</table>
		</center>
	</body>
</html>