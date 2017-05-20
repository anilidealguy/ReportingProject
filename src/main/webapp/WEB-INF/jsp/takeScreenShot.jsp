<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
<head>

</head>
<body>

		<div style="width:100px; border:solid 1px; float:left">
			Position
			<center>
				<button onclick="incTop()">^</button><br>
				<button onclick="decLeft()"><</button> <button onclick="incLeft()">></button><br>
				<button onclick="decTop()">v</button>
			</center>
		</div>

		<div style="width:100px; border:solid 1px; margin-left:120px; ">
			Dimensions
			<center>
				<button onclick="decHeight()">^</button><br>
				<button onclick="decWidth()"><</button> <button onclick="incWidth()">></button><br>
				<button onclick="incHeight()">v</button>
			</center>
		</div>
		<br>
		
		<div style="width:100px; height:100px; border:solid 1px; float:left">
			<center>
			Change Factor<br>
			<br/>
			<input type="text" id="cf" onchange="chFact(this.value)" style="width:75px;"><p id="myP">10</p>
			</center>
		</div>
		
		<div style="width:250px; height:100px; border:solid 1px; margin-left:120px; ">
			Current Measures<br><br>
			<table>
				<tr>
					<td>X: <input type="text" id="cmX" style="width:75px;"></td>
					<td>Y: <input type="text" id="cmY" style="width:75px;"></td>
				</tr>
				<tr>
					<td>W: <input type="text" id="cmW" style="width:75px;"></td>
					<td>H: <input type="text" id="cmH" style="width:75px;"></td>
				</tr>
			</table>
		</div>
		
<br>
<div style="position:relative">
<img src="<c:url value='/images/screenshot.png'/>"/>
<div id="myDIV" style="top:0px; width:100px; height:100px; left:0px;background-color: rgba(0, 0, 0, 0.5);; color: white;position:absolute;display:block">
  <h1><center>Crop<br>Area</center></h1>
</div>
</div>
<%-- <form:form action="/takeScreenShot.html" modelAttribute="string"> 
	<form:input path="retVal"/>
	<input type="submit" value="submit"> 
</form:form> --%>
<spring:url value="/capture.html" var="capture"></spring:url>
<form action ="${capture}" method="POST"> 
	<input type="text" name="retVal" id="retVal"/>
	<input type="submit" value="submit"> 
</form>

<script>
	var widthG=100;
    var heightG=100;
	var topG=0;
	var leftG=0;
	
	var changeFactor = 10;
	function chFact(val){
		changeFactor = parseInt(val);
		document.getElementById("myP").innerHTML = changeFactor;
	}
	
	function fillValues(){
		document.getElementById("cmX").value = leftG;
		document.getElementById("cmY").value = topG;
		document.getElementById("cmW").value = widthG;
		document.getElementById("cmH").value = heightG;
		document.getElementById("retVal").value = leftG + ";" + topG + ";" + widthG + ";" + heightG;
	}
	
	document.getElementById("myDIV").innerHTML = cssString;
	function incTop(){
		topG -=changeFactor;
		myFunction(topG, leftG, widthG, heightG);
	}
	
	function decTop(){
		topG +=changeFactor;
		myFunction(topG, leftG, widthG, heightG);
	}
	
	function incLeft(){
		leftG +=changeFactor;
		myFunction(topG, leftG, widthG, heightG);
	}
	
	function decLeft(){
		leftG -=changeFactor;
		myFunction(topG, leftG, widthG, heightG);
	}
	
	function incHeight(){
		heightG +=changeFactor;
		myFunction(topG, leftG, widthG, heightG);
	}
	
	function decHeight(){
		heightG -=changeFactor;
		myFunction(topG, leftG, widthG, heightG);
	}
	
	function incWidth(){
		widthG +=changeFactor;
		myFunction(topG, leftG, widthG, heightG);
	}
	
	function decWidth(){
		widthG -=changeFactor;
		myFunction(topG, leftG, widthG, heightG);
	}
	
function myFunction(tp, lt, wd, ht) {
	/*var width="500px";
    var height="500px";
	var top="100px";
	var left="600px";*/
	
	var cssString = "top:"+tp+"px; width:"+wd+"px; height:"+ht+"px; left:"+lt+"px;background-color: rgba(0, 0, 0, 0.5); color: white; position:absolute; display:block";
	fillValues();
	//document.getElementById("myDIV").innerHTML = cssString;
	document.getElementById("myDIV").style.cssText = cssString;
    /*document.getElementById("myDIV").style.width = width;
	document.getElementById("myDIV").style.height = height;
	document.getElementById("myDIV").style.top = top;
	document.getElementById("myDIV").style.left = left;*/
}
</script>


</body>
</html>
