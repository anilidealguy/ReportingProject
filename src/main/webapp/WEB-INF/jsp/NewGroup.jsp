<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<title>New Group</title>
		<style>
			table {
				font-family: arial, sans-serif;
				border-collapse: collapse;
				
				font-size: 15px;
			}

			td, th {
				border: 1px solid #ffffff;
				text-align: left;
				padding: 8px;
			}

			tr:nth-child(odd) {
				background-color: #a0d1e5;
			}
			
			tr:nth-child(even) {
				background-color: #dddddd;
			}
			
			th{
				background-color: #0e98d1;
			}
			
			
		</style>
	</head>
	<body>
		<br/>
		<br/>
		<center>
		<h1>Create A New Group</h1>
		<br/>
		<br/>
		<spring:url value="/saveGroup.html" var="saveGroup"/>
		<form:form action="${saveGroup}" method="post" modelAttribute="group">
			<table>
				<tr>
					<td><label>New Group Name</label></td>
					<td><form:input path="groupName"/></td>
				</tr>
				<tr>
					<td><center><input type="reset" value="Reset"/></center></td>
					<td><center><input type="submit" value="Create New Group"/></center></td>
				</tr>
			</table>
		</form:form>
		</center>
	</body>
</html>