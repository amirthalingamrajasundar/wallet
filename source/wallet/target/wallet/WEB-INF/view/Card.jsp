<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
<script>
	function validateForm() {
		let cardnumber = document.forms["cardAddForm"]["cardnumber"].value;
		let cvv = document.forms["cardAddForm"]["cvv"].value;
        let expiry = document.forms["cardAddForm"]["expiry"].value;
		if (!cardnumber) {
			alert("Please enter the card number !!");
			return false;
		}
		else if (cardnumber.length !== 16) {
			alert("Invalid card number. The number of digits in the card number should be 16 !!");
			return false;
		}

        if (!cvv) {
			alert("Please enter the CVV !!");
			return false;
		}
		else if (cvv.length !== 3) {
			alert("Invalid CVV. The number of digits in the CVV should be 3 !!");
			return false;
		}

        if (!expiry) {
			alert("Please enter the expiry date !!");
			return false;
		}
        else if (Date.parse(expiry) < new Date().getTime()) {
            alert("The expiry date of the card should be greater than today");
			return false;
        }
	}
</script>
<style>
    a:link {
        color: blue;
    }
    a:hover {
        color: darkblue;
    }
</style>
</head>
<body>
	<div class="cardsTable" style="float: right;">
        <c:if test="${requestScope.deletecarderror != ''}">
			<p style="color: red;"><c:out value="${requestScope.deletecarderror}"/></p>
		</c:if>
		<c:if test="${requestScope.deletecardsuccess != ''}">
			<p style="color: green;"><c:out value="${requestScope.deletecardsuccess}"/></p>
		</c:if>
		<table>
		<thead>
			<tr><td colspan="5"><b style="color: black;">My Cards...</b></td></tr>
			<tr>
				<th scope="col" colspan="1">Card Number</th>
				<th scope="col" colspan="1">Expiry date</th>
				<th scope="col" colspan="1">CVV</th>
                <th scope="col" colspan="1">Action</th>

			</tr>
		</thead>
		<tbody>
		<c:forEach items="${cards}" var="element">
			<tr>
				<td>${element.getCardNumber()}</td>
				<td>${element.getExpiry().toString()}</td>
				<td>${element.getCVV()}</td>
                <td><a href="deleteCard?id=${element.getId()}">delete</a></td>
			</tr>
		</c:forEach>
			
		</tbody>
		</table>
	</div>
	<div class="container">
		<h3>Add a new card.</h3>
		<c:if test="${requestScope.cardError != ''}">
			<p style="color: red;"><c:out value="${requestScope.cardError}"/></p>
		</c:if>
		<c:if test="${requestScope.cardSuccess != ''}">
			<p style="color: green;"><c:out value="${requestScope.cardSuccess}"/></p>
		</c:if>
		<br>
		<form name = "cardAddForm"  action="addCard" method="post" onsubmit="return validateForm()">
			<label for="cardnumber">Card Number</label> 
			<input type="text" name="cardnumber" placeholder="Enter card number"/>
			<br>
			<br>
			<label for="expiry">Expiry date</label> 
			<input type="date" name="expiry" placeholder="Enter expiry date"/>
			<br>
			<br>
			<label for="cvv">CVV</label> 
			<input type="text" name="cvv" placeholder="Enter CVV"/>
			<input type="submit" value="Add card">
		</form>
		
	</div>
</body>
</html>