package com.laptrinhmang.asyncapp.model.worker;

import com.laptrinhmang.asyncapp.model.bean.ProcessingTask;
import com.laptrinhmang.asyncapp.model.dao.TaskDAO;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
public class PDFProcessingWorker implements Runnable{
	private final int taskId;
	private final String pdfFilePath;
	private final TaskDAO taskDAO;
	private static final String RESULT_DIR = "E:/async_results/";
	public PDFProcessingWorker(int taskId,String pdfFilePath, TaskDAO taskDAO) {
		this.taskId = taskId;
		this.pdfFilePath = pdfFilePath;
		this.taskDAO = taskDAO;
	}
	@Override
	public void run() {
		String resultPath = null;
		try {
			taskDAO.updateTaskStatus(taskId,"PROCESSING");
			System.out.println("Task ID " + taskId + ": Bắt đầu xử lý file...");
			File pdfFile = new File(pdfFilePath);
			String extractedText="";
			try (PDDocument document = PDDocument.load(pdfFile)){
				PDFTextStripper stripper = new PDFTextStripper();
				extractedText = stripper.getText(document);
			}
			
			int wordCount = 0;
			if (extractedText != null && !extractedText.trim().isEmpty()) {
                wordCount = extractedText.split("\\s+").length;
            }
			resultPath = RESULT_DIR + "result_" + taskId + ".txt";
			File resultFile = new File(resultPath);
			resultFile.getParentFile().mkdirs();
			try (FileWriter writer = new FileWriter(resultFile)) {
                writer.write("--- TÓM TẮT XỬ LÝ ---\n");
                writer.write("Số từ đếm được: " + wordCount + "\n\n");
                writer.write("--- NỘI DUNG TRÍCH XUẤT ---\n");
                writer.write(extractedText);
            }
			String summary = "Hoàn thành. Số từ: " + wordCount;
            taskDAO.updateTaskResult(taskId, summary, resultPath); 
            taskDAO.updateTaskStatus(taskId, "COMPLETED");
            File originalPdfFile = new File(pdfFilePath);
            if (originalPdfFile.exists()) {
                originalPdfFile.delete();
                System.out.println("Task ID " + taskId + ": Đã dọn dẹp file PDF gốc.");
            }
            System.out.println("Task ID " + taskId + ": Xử lý thành công. Số từ: " + wordCount);
		}catch (SQLException eSQL) {
            System.err.println("Lỗi DB Worker Task " + taskId + ": " + eSQL.getMessage());
            try {
                taskDAO.updateTaskStatus(taskId, "FAILED");
            } catch (SQLException ignored) {  }
        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Lỗi Xử lý File Worker Task " + taskId + ": " + e.getMessage());
             try {
                taskDAO.updateTaskStatus(taskId, "FAILED");
            } catch (SQLException ignored) { }
        }
	}
	public static void main(String[] args) {
        TaskDAO taskDAO = new TaskDAO();
        int userId = 1; 
        String validFilePath = "E:/test.pdf"; 
        String invalidFilePath = "/path/to/non_existent_file.pdf";
        try {
            System.out.println("--- TEST 1: KỊCH BẢN THÀNH CÔNG ---");
            
            // BƯỚC A: Tạo Task (status PENDING)
            ProcessingTask p1 = new ProcessingTask(userId, "test.pdf", validFilePath);
            int taskId1 = taskDAO.createTask(p1);
            System.out.println("   -> Task PENDING ID: " + taskId1);

            // BƯỚC B: Chạy Worker (chạy trực tiếp hàm run(), không dùng Executor)
            PDFProcessingWorker worker1 = new PDFProcessingWorker(taskId1, validFilePath, taskDAO);
            worker1.run(); 
            
            System.out.println("   -> KẾT QUẢ DB: Kiểm tra bảng processing_tasks. Status phải là COMPLETED.");
            
        } catch (SQLException e) {
            System.err.println("Lỗi SQL trong quá trình TEST: " + e.getMessage());
        }

        
    }
}