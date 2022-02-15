
<%--
  Created by IntelliJ IDEA.
  User: Yuriy
  Date: 15.02.2022
  Time: 5:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Add User</title>
</head>
<body>
    <form action="meals?action=add" method="post">
        <label> DateTime:
            <input type="datetime-local" name="date">
        </label>
        <br>
        <label> Description
            <input type="text" name="description">
        </label>
        <br>
        <label> Calories
            <input type="text" name="calories">
        </label>
        <br>
        <input type="submit" value="Submit">
    </form>

</body>
</html>
