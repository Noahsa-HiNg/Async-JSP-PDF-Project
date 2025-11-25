<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Dashboard</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <link href='https://unpkg.com/boxicons@2.1.4/css/boxicons.min.css' rel='stylesheet'>
    <meta http-equiv="refresh" content="5">
</head>
<body>
    <div class="wrapper wide">
        <div style="display: flex; justify-content: space-between; align-items: center; margin-bottom: 20px;">
            <h1>Task Dashboard</h1>
            <div>
                <a href="${pageContext.request.contextPath}/upload" class="btn btn-sm" style="width: auto;">+ New Upload</a>
                <a href="${pageContext.request.contextPath}/logout" class="btn btn-sm" style="background: #e74c3c; color: white; width: auto;">Logout</a>
            </div>
        </div>

        <div class="table-container">
            <table>
                <thead>
                    <tr>
                        <th>Time</th>
                        <th>File Name</th>
                        <th>Status</th>
                        <th>Result</th>
                        <th>Action</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="task" items="${taskList}">
                        <tr>
                            <td style="white-space: nowrap;">
                                <fmt:formatDate value="${task.createdAt}" pattern="HH:mm dd/MM/yyyy" />
                            </td>

                            <td>${task.fileName}</td>
                            <td>
                                <c:choose>
                                    <c:when test="${task.status == 'PENDING'}"><span class="badge badge-pending">Pending</span></c:when>
                                    <c:when test="${task.status == 'PROCESSING'}"><span class="badge badge-processing">Processing</span></c:when>
                                    <c:when test="${task.status == 'COMPLETED'}"><span class="badge badge-completed">Completed</span></c:when>
                                    <c:otherwise><span class="badge badge-failed">Failed</span></c:otherwise>
                                </c:choose>
                            </td>
                            <td>${task.resultSummary}</td>
                            <td>
                                <c:if test="${task.status == 'COMPLETED'}">
                                    <a href="${pageContext.request.contextPath}/download?taskId=${task.id}" class="btn btn-sm">
                                        <i class='bx bxs-download'></i> Download
                                    </a>
                                </c:if>
                            </td>
                        </tr>
                    </c:forEach>
                    <c:if test="${empty taskList}">
                        <tr>
                            <td colspan="5" style="text-align: center; padding: 30px;">No tasks found. Start by uploading a file!</td>
                        </tr>
                    </c:if>
                </tbody>
            </table>
        </div>
    </div>
</body>
</html>