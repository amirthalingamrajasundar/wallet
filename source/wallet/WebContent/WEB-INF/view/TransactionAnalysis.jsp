<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/css/login.css">
</head>
<body>
    <c:if test="${analysis != null && analysis.size() > 0}">
        <div class="transAnalysisTable" style="float: right;">
            <table>
            <thead>
                <tr><td colspan="5"><b style="color: blue;">Transaction Analysis - ${periodicity}</b></td></tr>
                <tr>
                    <th scope="col" colspan="1">Period</th>
                    <th scope="col" colspan="1">Total Transaction Amount</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${analysis}" var="element">
                <tr>
                    <td>${element.get(0)}</td>
                    <td>${element.get(1)}</td>
                </tr>
            </c:forEach>
                
            </tbody>
            </table>
            <br/>
            <br/>
            <span>${misctext}</span>
        </div>
    </c:if>
	<div class="container">
		<h3>Select periodicity</h3>
		<c:if test="${requestScope.transError != ''}">
			<p style="color: red;"><c:out value="${requestScope.transError}"/></p>
		</c:if>
		<c:if test="${requestScope.transSuccess != ''}">
			<p style="color: green;"><c:out value="${requestScope.transSuccess}"/></p>
		</c:if>
		<br>
		<form action="transAnalyze" method="get">
			<label for="periodicity">Periodicity</label>
            <select id="periodicity" name="periodicity">
                <option value="yearly" ${periodicity != null && periodicity.equals("yearly") ? "selected": ""}>Yearly</option>
                <option value="monthly" ${periodicity != null && periodicity.equals("monthly") ? "selected": ""}>Monthly</option>
                <option value="daily" ${periodicity != null && periodicity.equals("daily") ? "selected": ""}>Daily</option>
            </select>
			<br>
			<input type="submit" value="Get Analysis">
		</form>
		
	</div>
</body>
</html>