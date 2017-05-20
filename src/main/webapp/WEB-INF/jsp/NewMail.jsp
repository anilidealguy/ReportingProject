<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<title>New Mail</title>
		<script type="text/javascript">
			function addRow(tableID) {
				var table = document.getElementById(tableID);
				var rowCount = table.rows.length;
				var row = table.insertRow(rowCount);
				var length = (table.rows.length)-1;
				
				var cell1 = row.insertCell(0);
	            var element1 = document.createElement("input");
	            element1.align="center";
	            element1.type = "checkbox";
	            element1.name="chkbox[]";
	            cell1.appendChild(element1);
	            
	            var cell2 = row.insertCell(1);
	            var element2 = document.createElement("input");
	            element2.type = "text";
	            element2.name = "lines["+length+"].lineNumber";
	            cell2.appendChild(element2);
	            
	            var cell3 = row.insertCell(2);
	            var element3 = document.createElement("select");
	            
	           	var lineTypes = new Array();
	           	<c:forEach items="${lineTypesList}" var="lineType" varStatus="status">
	           		lineTypesO = new Object();
	           		lineTypesO.id = ${lineType.id};
	           		lineTypesO.name = "${lineType.name}";
	           		lineTypes.push(lineTypesO);
	           	</c:forEach>
	            
	            
	            for (var i = 0; i < lineTypes.length; i++) {
					var option = document.createElement("option");
					option.value = lineTypes[i].id;
					option.text = lineTypes[i].name;
					element3.appendChild(option);
				}
	            element3.name = "lines["+length+"].lineType";
	            cell3.appendChild(element3);
	            
	            var cell4 = row.insertCell(3);
	            var element4 = document.createElement("input");
	            element4.type = "text";
	            element4.name = "lines["+length+"].arg1";
	            cell4.appendChild(element4);
	            
	            var cell5 = row.insertCell(4);
	            var element5 = document.createElement("input");
	            element5.type = "text";
	            var length = (table.rows.length)-1;
	            element5.name = "lines["+length+"].arg2";
	            cell5.appendChild(element5);
	            
	            var cell6 = row.insertCell(5);
	            var element6 = document.createElement("input");
	            element6.type = "text";
	            element6.name = "lines["+length+"].arg3";
	            cell6.appendChild(element6);  
	            
	            var cell7 = row.insertCell(6);
	            var element7 = document.createElement("input");
	            element7.type = "text";
	            element7.name = "lines["+length+"].arg4";
	            cell7.appendChild(element7); 
	            
			}
			
			function deleteRow(tableID) {
	            try {
	            var table = document.getElementById(tableID);
	            var rowCount = table.rows.length;

	            for(var i=0; i<rowCount; i++) {
	                var row = table.rows[i];
	                var chkbox = row.cells[0].childNodes[0];
	                if(null != chkbox && true == chkbox.checked) {
	                    table.deleteRow(i);
	                    rowCount--;
	                    i--;
	                }
	            }
	            }catch(e) {
	                alert(e);
	            }
	        }
		</script>
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
			#metadata{
				float:left;
			}
			#discription{
				margin-left:30%;
			}
			
			
		</style>
	</head>
	
	<body>
		<h1>Mail Name</h1><br/>
		<h2>Mail Meta Data</h2>
		<spring:url value="/saveMail.html" var="saveMail"/>
		<form:form commandName="mail" action="${saveMail}" method="post">
			<table id="metadata">
				<%-- <tr>
					<td>Mail Name</td>
					<td>
						<form:select path="mailName" >
							<c:forEach var="group" items="${groupsList}">
								<form:option items="${group.groupName}" value="${group.id}" label="${group.groupName}"></form:option>
							</c:forEach>
							
						</form:select>
						<a href="/newGroup.html"><label>New</label></a> 
					</td>
				</tr>
				
				<tr>
					<td>Report Sub Group</td>
					<td>
						<form:select path="subGroupId" >
							<c:forEach var="subGroup" items="${subGroupsList}">
								<form:option items="${subGroup.subGroupName}" value="${subGroup.id}" label="${subGroup.subGroupName}"></form:option>
							</c:forEach>
							
						</form:select>
						<a href="newSubGroup.html"><label>New</label></a> 
					</td>
				</tr> --%>
				
				<tr>
					<td>Mail Name</td>
					<td><form:input path="mailName"/></td>
				</tr>
				
				<tr>
					<td>To List</td>
					<td><form:input path="toList"/></td>
				</tr>
				
				<tr>
					<td>Cc List</td>
					<td><form:input path="ccList"/></td>
				</tr>
				
				<tr>
					<td>Subject</td>
					<td><form:input path="subject"/></td>
				</tr>
				
				<tr>
					<td>Message Location</td>
					<td><form:input path="msgLocation"/></td>
				</tr>
				
				<tr>
					<td>Save</td>
					<td><form:input path="save"/></td>
				</tr>
				
				<tr>
					<td>Send</td>
					<td><form:input path="send"/></td>
				</tr>
											
			</table>
			<table id="discription">
				<tr>
					<th>Line Type</th>
					<th>Arg 1</th>
					<th>Arg 2</th>
					<th>Arg 3</th>
					<th>Arg 4</th>
				</tr>
				<tr>
					<td>Plain</td>
					<td>What ever you want to write</td>
					<td>NA</td>
					<td>NA</td>
					<td>NA</td>
				</tr>
				
				<tr>
					<td>Link</td>
					<td>Text before link(Here is the link for...)</td>
					<td>Link to(http://www.google.com)</td>
					<td>Display Text(Click Me)</td>
					<td>After Text(.)</td>
				</tr>
				
				<tr>
					<td>Image</td>
					<td>Webpage Address(http://www.google.com)</td>
					<td>NA</td>
					<td>Zoom Factor(1,2,3)</td>
					<td>NA</td>
				</tr>
				
				<tr>
					<td>Headding</td>
					<td>Text</td>
					<td>NA</td>
					<td>NA</td>
					<td>NA</td>
				</tr>
				
				<tr>
					<td>Blank</td>
					<td>NA</td>
					<td>NA</td>
					<td>NA</td>
					<td>NA</td>
				</tr>
			</table>
			
			
			<br>
			<br>
			<br>
			<h2>Mail Body</h2>
			<INPUT type="button" value="Add Row" onclick="addRow('dataTable')" />	 
			<INPUT type="button" value="Delete Row" onclick="deleteRow('dataTable')" />
			<br>
			<table id="dataTable">
				<tr>
					<th>Check Box</th>
					<th>Line Number</th>
					<th>Line Type</th>
					<th>Argument 1</th>
					<th>Argument 2</th>
					<th>Argument 3</th>
					<th>Argument 4</th>
				</tr>
				
				
			</table>	
			
			<br><br>
			<input type="reset" value="Reset"/>
			<input type="submit" value="Save"/>
			<br><br><br><br>
		</form:form>
	</body>
</html>