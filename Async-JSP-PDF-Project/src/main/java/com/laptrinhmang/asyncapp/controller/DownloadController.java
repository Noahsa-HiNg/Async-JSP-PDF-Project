package com.laptrinhmang.asyncapp.controller;

import com.laptrinhmang.asyncapp.model.bean.ProcessingTask;
import com.laptrinhmang.asyncapp.model.dao.TaskDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;

@WebServlet("/download")
public class DownloadController extends HttpServlet {

    private TaskDAO taskDAO;

    public void init() {
        taskDAO = new TaskDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("userId") == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Bạn cần đăng nhập để tải file.");
            return;
        }
        int currentUserId = (int) session.getAttribute("userId");

        int taskId;
        try {
            taskId = Integer.parseInt(request.getParameter("taskId"));
        } catch (NumberFormatException e) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Task ID không hợp lệ.");
            return;
        }

        try {
            ProcessingTask task = taskDAO.getTaskById(taskId);

            if (task == null || task.getUserId() != currentUserId) {
                response.sendError(HttpServletResponse.SC_FORBIDDEN, "Bạn không có quyền truy cập file này.");
                return;
            }

            if (!"COMPLETED".equals(task.getStatus())) {
                response.sendError(HttpServletResponse.SC_CONFLICT, "File này chưa được xử lý xong.");
                return;
            }

            File fileToDownload = new File(task.getResultPath());
            if (!fileToDownload.exists()) {
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "File kết quả không còn tồn tại trên server.");
                return;
            }

            response.setContentType("application/msword");
            response.setContentLength((int) fileToDownload.length());

            String originalName = task.getFileName();
            String downloadName = "document.doc"; 

            if (originalName != null && !originalName.isEmpty()) {
                int lastDotIndex = originalName.lastIndexOf(".");
                if (lastDotIndex > 0) {
                    downloadName = originalName.substring(0, lastDotIndex) + ".doc";
                } else {
                    downloadName = originalName + ".doc";
                }
            }

            String headerKey = "Content-Disposition";

            String headerValue = String.format("attachment; filename=\"%s\"", downloadName);
            response.setHeader(headerKey, headerValue);

            try (FileInputStream fileIn = new FileInputStream(fileToDownload);
                 OutputStream out = response.getOutputStream()) {
                
                byte[] buffer = new byte[4096];
                int bytesRead;
                
                while ((bytesRead = fileIn.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }

        } catch (IOException e) {
            System.err.println("Lỗi I/O khi stream file: " + e.getMessage());
        }
    }
}