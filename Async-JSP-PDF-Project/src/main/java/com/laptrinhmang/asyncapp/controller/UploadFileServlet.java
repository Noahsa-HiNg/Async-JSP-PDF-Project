package com.laptrinhmang.asyncapp.controller;

import com.laptrinhmang.asyncapp.model.bean.ProcessingTask;
import com.laptrinhmang.asyncapp.model.dao.TaskDAO;
import com.laptrinhmang.asyncapp.model.service.TaskQueueService;
import com.laptrinhmang.asyncapp.model.worker.PDFProcessingWorker;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig; // QUAN TRỌNG CHO UPLOAD
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/upload")
@MultipartConfig(fileSizeThreshold = 1024 * 1024 * 2, // 2MB
                 maxFileSize = 1024 * 1024 * 100,  // 100MB
                 maxRequestSize = 1024 * 1024 * 150) // 150MB
public class UploadFileServlet extends HttpServlet {
	private static final String UPLOAD_DIR = "E:/async_results/";
	private TaskDAO taskDAO;
	public void init() {
		taskDAO = new TaskDAO();
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if (session == null || session.getAttribute("userId") == null) {
			response.sendRedirect(request.getContextPath()+"/login");
			return;
		}
		int currentId = (int)session.getAttribute("userId");
		File uploadDir = new File(UPLOAD_DIR);
		if (!uploadDir.exists()) {
			uploadDir.mkdir();
		}
		try {
			Part filePart = request.getPart("pdfFile");
			String fileName = filePart.getSubmittedFileName();
			String tempFilePath = UPLOAD_DIR + System.currentTimeMillis() + "_" + fileName;
			filePart.write(tempFilePath);
			ProcessingTask newTask = new ProcessingTask(currentId,fileName,tempFilePath);
			int TaskId = taskDAO.createTask(newTask);
			PDFProcessingWorker worker = new PDFProcessingWorker(TaskId, tempFilePath, taskDAO);
			response.sendRedirect(request.getContextPath() + "/status");
		}catch (SQLException e) {
            request.setAttribute("error", "Lỗi DB khi tạo Task.");
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        } catch (Exception e) {
            request.setAttribute("error", "Lỗi khi xử lý file upload: " + e.getMessage());
            request.getRequestDispatcher("/index.jsp").forward(request, response);
        }
	}
}