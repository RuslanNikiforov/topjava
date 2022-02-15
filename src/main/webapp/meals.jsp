<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page import="ru.javawebinar.topjava.model.MealTo" %>
<%@ page import="java.util.List" %>
<%@ page import="java.sql.Time" %>
<%@ page import="ru.javawebinar.topjava.util.TimeUtil" %><%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 13.02.2022
  Time: 22:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
    <h3><a href="index.html">Home</a></h3>
    <h1>Meals</h1>

    <h4><a href="addUser.jsp"> Add User </a></h4>

    <table border="2">


        <jsp:useBean id="mealsToList" scope="request" type="java.util.List"/>

        <c:forEach var="meal" items="${mealsToList}">
            <c:choose>
                <c:when test="${meal.excess == true}">
                    <tr style="color: red">
                        <td><c:out value="${meal.dateTime}"/></td>
                        <td><c:out value="${meal.description}" /> </td>
                        <td><c:out value="${meal.calories}" />  </td>
                        <td><a href="meals?action=delete&id=${meal.id}">Delete</a><td>
                    <tr>
                </c:when>
                <c:otherwise>
                    <tr style="color: green">
                        <td><c:out value="${meal.dateTime}" /> </td>
                        <td><c:out value="${meal.description}" /> </td>
                        <td><c:out value="${meal.calories}" />  </td>
                        <td><a href="meals?action=delete&id=${meal.id}">Delete</a><td>
                    <tr>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </table>


</body>
</html>
