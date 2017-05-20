<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
	<head>
		<title>Report Name</title>
		<script type="text/javascript">
			function testing(ele){
				console.log(ele.options[ele.selectedIndex].text);
				var selectedText = ele.options[ele.selectedIndex].text;
				var tableElement = ele.parentElement.parentElement.childNodes[3];
				var childsBefore = tableElement.firstChild;
				
				if(selectedText == 'Mail'){
					var childs = tableElement.firstChild;
					console.log("childs: " + childs);
					nextCol = document.createElement("select");
					nextCol.name = childs.name;
					var mails = new Array();
		           	<c:forEach items="${mailList}" var="mail" varStatus="status">
		           		mailO = new Object();
		           		mailO.id = "${mail.id}";
		           		mailO.name = "${mail.subject}";
		           		mails.push(mailO);
		           	</c:forEach>
		            
		            
		            for (var i = 0; i < mails.length; i++) {
						var option = document.createElement("option");
						option.value = mails[i].id;
						option.text = mails[i].name;
						nextCol.appendChild(option);
					}
		           tableElement.replaceChild(nextCol,childs);
				}/* else if(selectedText == 'DownloadTableau'){
					var childs = tableElement.firstChild;
					console.log("childs: " + childs.name);
					nextCol = document.createElement("select");
					nextCol.name = childs.name;
					console.log(nextCol);
					var workbookss = new Array();
		           	<c:forEach items="${workbooks}" var="workbook" varStatus="status">
		           		workbookO = new Object();
		           		workbookO.id = "${workbook.id}";
		           		workbookO.name = "${workbook.project.name} --> ${workbook.name}";
		           		workbookss.push(workbookO);
		           	</c:forEach>
		            
		            
		            for (var i = 0; i < workbookss.length; i++) {
						var option = document.createElement("option");
						option.value = workbookss[i].id;
						option.text = workbookss[i].name;
						nextCol.appendChild(option);
					}
		            tableElement.replaceChild(nextCol,childs);
				}   */
			}
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
	            element2.name = "steps["+length+"].sequenceNumber";
	            cell2.appendChild(element2);
	            
	            var cell3 = row.insertCell(2);
	            var element3 = document.createElement("select");
	            
	           	var actionTypes = new Array();
	           	<c:forEach items="${actionTypesList}" var="actionType" varStatus="status">
	           		actionTypesO = new Object();
	           		actionTypesO.id = ${actionType.id};
	           		actionTypesO.name = "${actionType.name}";
	           		actionTypes.push(actionTypesO);
	           	</c:forEach>
	            
	            
	            for (var i = 0; i < actionTypes.length; i++) {
					var option = document.createElement("option");
					option.value = actionTypes[i].id;
					option.text = actionTypes[i].name;
					element3.appendChild(option);
				}
	            element3.name = "steps["+length+"].actionType";
	            //element3.onchange("testing()");
	            element3.setAttribute("onchange","testing(this)");
	            console.log(element3);
	            cell3.appendChild(element3);
	            
	            var cell4 = row.insertCell(3);
	            var element4 = document.createElement("input");
	            element4.type = "text";
	            element4.name = "steps["+length+"].arg1";
	            cell4.appendChild(element4);
	          
	            var cell5 = row.insertCell(4);
	            var element5 = document.createElement("input");
	            element5.type = "text";
	            var length = (table.rows.length)-1;
	            element5.name = "steps["+length+"].arg2";
	            cell5.appendChild(element5);
	            
	            var cell6 = row.insertCell(5);
	            var element6 = document.createElement("input");
	            element6.type = "text";
	            element6.name = "steps["+length+"].arg3";
	            cell6.appendChild(element6);  
	            
	            var cell7 = row.insertCell(6);
	            var element7 = document.createElement("input");
	            element7.type = "text";
	            element7.name = "steps["+length+"].arg4";
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
		<h1>Report Name</h1><br/>
		<h2>Report Meta Data</h2>
		<spring:url value="/saveReport.html" var="saveReport"/>
		<form:form commandName="report" action="${saveReport}" method="post">
			<table id="metadata">
				<tr>
					<td>Report Group</td>
					<td>
						<form:select path="groupId" >
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
				</tr>
				
				<tr>
					<td>Report Name</td>
					<td><form:input path="name"/></td>
				</tr>
											
			</table>
			
			<table id="discription">
				<tr>
					<th>Action Type</th>
					<th>Arg 1</th>
					<th>Arg 2</th>
					<th>Arg 3</th>
					<th>Arg 4</th>
				</tr>
				<tr>
					<td>DownloadTableau</td>
					<td>Select the tableau report from the list</td>
					<td>Local Folder Location with file name (C:\UniperReporting\XPS.twbx)</td>
					<td>NA</td>
					<td>NA</td>
				</tr>
				
				<tr>
					<td>UploadSharepoint</td>
					<td>Local file path (C:\UniperReporting\XPS.twbx)</td>
					<td>Destination folder path(C:\UniperReporting\)</td>
					<td>NA</td>
					<td>NA</td>
				</tr>
				
				<tr>
					<td>Mail</td>
					<td>Select from the list</td>
					<td>NA</td>
					<td>NA</td>
					<td>NA</td>
				</tr>
			</table>
			
			<br>
			<br>
			<h2>Procedure</h2>
			<INPUT type="button" value="Add Row" onclick="addRow('dataTable')" />	 
			<INPUT type="button" value="Delete Row" onclick="deleteRow('dataTable')" />
			<br>
			<table id="dataTable">
				<tr>
					<th>Check Box</th>
					<th>Sequence Number</th>
					<th>Action Type</th>
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