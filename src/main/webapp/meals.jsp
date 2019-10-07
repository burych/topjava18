<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table border="1">
    <tr>
        <td>Date</td>
        <td>Name</td>
        <td>Calories</td>
    </tr>

    <c:forEach var="meal" items="${meals}">
        <c:set var="col" value="green"/>
        <c:if test="${meal.isExcess()}">
            <c:set var="col" value="red"/>
        </c:if>

        <tr style="color: ${col}">
            <td>${meal.getDateAndTime()}</td>
            <td>${meal.getDescription()}</td>
            <td>${meal.getCalories()}</td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
