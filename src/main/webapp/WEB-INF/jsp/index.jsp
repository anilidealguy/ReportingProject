<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<title>Home</title>
		<script type="text/javascript">
			function hide(ele){
				console.log("calling");
				var tr = document.getElementById(ele.name);
				
				var button = document.getElementsByName(ele.name);
				
				if(tr.style.visibility == 'visible'){
					tr.style.visibility  = 'collapse';
				}else{
					tr.style.visibility  = 'visible';
				}
			}
			function checkAll(ele) {
				console.log(ele)
				var checkboxes = document.getElementsByClassName(ele.className);
				
				if (ele.checked) {
					for (var i = 0; i < checkboxes.length; i++) {
						if (checkboxes[i].type == 'checkbox') {
						checkboxes[i].checked = true;
					}
				}
				} else {
					for (var i = 0; i < checkboxes.length; i++) {
						if (checkboxes[i].type == 'checkbox') {
							checkboxes[i].checked = false;
						}
					}
				}
			}
		</script>
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
				width: 70%;
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
		<center><h1>Enterprise Reporting</h1></center>
		
		<div style="width:10%;float:left;">
			<table>
				<tr><td><a href="<%=request.getContextPath() %>/groupList.html">Group</a></td></tr>
				<tr><td><a href="<%=request.getContextPath() %>/newSubGroup.html">Sub Group</a></td></tr>
				<tr><td><a href="<%=request.getContextPath() %>/newReport.html">Report</a></td></tr>
				<tr><td><a href="<%=request.getContextPath() %>/newMail.html">Mail</a></td></tr>
				<tr><td><a href="#">Action Type</a></td></tr>
				<tr><td><a href="#">Line Type</a></td></tr>
			</table>
		</div>
		
		<center>
		<spring:url value="/runReports.html" var="runReports"/>
		<form:form method="post" action="${runReports}" modelAttribute="listReports">
		<table>
			<tr>
				<td>Date</td>
				<td><form:input path="date"/>(Ex. 20170515)</td>
			<!-- </tr>
			<tr> -->
				<td>Seperator</td>
				<td><form:input path="seperator"/></td>
			</tr>
		</table>
		<br><br>
		
		<table border="1px" >
			<tr>
				<th>Report Group</th>
				<th>Report Sub Group</th>
				<th>Report Name</th>
				<!-- <th rowspan="2">Download(.twbx)</th>
				<th rowspan="2">Upload to Sharepoint</th>
				<th rowspan="2">Save Mail</th>
				<th rowspan="2">Send Mail</th> -->
				<th>Select All</th>
			</tr>
			
			
			<%-- <tr>
				<th><center><input type="button" onclick="location.href='/newGroup.html'" value="New Group"/></center></th>
				<th><center><input type="button" onclick="location.href='/newSubGroup.html'" value="New Sub Group"/></center></th>
				<th><center>
				<input type="button" onclick="location.href='/newReport.html'" value="New Report"/>
				<input type="button" onclick="location.href='/newMail.html'" value="New Mail"/>
				</center></th>
				
			</tr> --%>
			
				
				<c:forEach var="report" items="${reportsList}" varStatus="status">
					<tr>
					<form:input path="listReports[${status.index}].id" value="${report.id}" type="hidden"></form:input>
					<td>${report.groupName}<form:input path="listReports[${status.index}].groupName" value="${report.groupName}" type="hidden"></form:input></td>
					<td>${report.subGroupName}<form:input path="listReports[${status.index}].subGroupName" value="${report.subGroupName}" type="hidden"></form:input></td>
					<td>${report.name}<form:input path="listReports[${status.index}].name" value="${report.name}" type="hidden"></form:input></td>
					<td><center>
					<input class="${report.name}" type="checkbox" onchange="checkAll(this)">
					<input type="button" name="${report.name}" onclick="hide(this)" value="Hide/UnHide"/>
					</center></td>
					</tr>
					
					<tr id="${report.name}" class="display">
					
					<td colspan=4>
					<c:forEach items="${report.steps}" var="step" varStatus="counter">
					
					<table style="width:18%;"><tr>
						<td style="width:10%;">${step.sequenceNumber}</td>
						<%-- <td style="width:80%;">${actionTypes}.${step.actionType}</td> --%>
						<td>
							<c:forEach items="${actionTypes}" var="entry">
								<c:if test="${step.actionType == entry.key}">
									${entry.value}
								</c:if>
								
							</c:forEach>
						</td>
						<td style="width:10%;"> 
							<form:checkbox class="${report.name}" path="listReports[${status.index}].steps[${counter.index}].active" value="true" />
							<form:input path="listReports[${status.index}].steps[${counter.index}].sequenceNumber" value="${step.sequenceNumber}" type="hidden"></form:input>
							<form:input path="listReports[${status.index}].steps[${counter.index}].actionType" value="${step.actionType}" type="hidden"></form:input>
						</td>
					</tr></table>
					</c:forEach>
					</td>
					
					<%-- ${report.steps} --%>
					<%-- <center><form:checkbox path="listReports[${status.index}].steps[0].active" value="true" /></center></td>
					<td><center><form:checkbox path="listReports[${status.index}].steps[0].active" value="true"/></center></td>
					<td><center><form:checkbox path="listReports[${status.index}].steps[0].active" value="true"/></center></td>
					<td><center><form:checkbox path="listReports[${status.index}].steps[0].active" value="true"/></center></td> --%>
					
					</tr>
		
					
				</c:forEach>
			
				<tr>
					<td colspan="2"></td>
					<td><input type="submit" value = "Run All"></td>
					<td><input type ="reset" value = "Reset All"></td>
				</tr>
			
			
			
		</table>
		</form:form>
		</center>
	</body>
</html>