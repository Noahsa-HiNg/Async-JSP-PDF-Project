<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head><title>Trạng thái Task</title></head>
<body>
    <h1>Trạng thái các Task</h1>
    
    <table border="1">
        <tr>
            <th>ID</th>
            <th>File</th>
            <th>Trạng thái</th>
            <th>Kết quả</th>
        </tr>
        <c:forEach var="task" items="${taskList}">
            <tr>
                <td>${task.id}</td>
                <td>${task.fileName}</td>
                <td>${task.status}</td>
                <td>${task.resultSummary}</td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>