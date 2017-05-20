<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Groups</title>
<style>
			.display{
				visibility: "collapse";
			}
			#expand{
				border: 1px solid #ffffff;
				width:25px;
				height:25px;
			}
			table {
				font-family: arial, sans-serif;
				border-collapse: collapse;
				width: 50%;
				font-size: 15px;
			}

			td, th {
				border: 1px solid #ffffff;
				text-align: left;
				padding: 8px;
			}
			
			tr:nth-child(even) {
				background-color: #dddddd;
				
			}
			
			tr:nth-child(odd) {
				background-color: #a0d1e5;
			}
			
			th{
				background-color: #0e98d1;
			}
			
			
			
		</style>
</head>
<body>
	<center>
	<h2>Groups</h2>
	<br><a href="<%=request.getContextPath() %>/newGroup.html">New Group</a><br>
	<table>
		<tr>
			<th>Group Name</th>
			<th>Edit</th>
			<th>Delete</th>
		</tr>
		<c:forEach var="group" items="${groupsList}" varStatus="i">
			<tr>
				<td>${group.groupName}</td>
				<td><center><a href="#">Edit</a></center></td>
				<td><center><a href="#">Delete</a></center></td>
			</tr>
		</c:forEach>
	</table>
	</center>
</body>
</html>