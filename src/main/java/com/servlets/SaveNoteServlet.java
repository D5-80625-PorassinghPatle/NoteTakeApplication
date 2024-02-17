package com.servlets;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.entities.Note;
import com.helper.FactoryProvider;

/**
 * Servlet implementation class SaveNoteServlet
 */
@MultipartConfig
public class SaveNoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SaveNoteServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			String title=request.getParameter("title");
			String content=request.getParameter("content");

			
			 // Part representing the image file input
	        Part filePart = request.getPart("image");

	        // Get the filename from the part
	        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();

	        // Get the input stream for the file
	        InputStream fileContent = filePart.getInputStream();

	        // Read the file content into a byte array
	        byte[] imageData = fileContent.readAllBytes();

	        // Now you have title, content, and imageData, you can proceed to save them using Hibernate or any other method
	        // For simplicity, let's just print them
	        System.out.println("Title: " + title);
	        System.out.println("Content: " + content);
	        System.out.println("Image File Name: " + fileName);
	        // Do something with imageData

	        // Don't forget to close the input stream
	        fileContent.close();
			
			Note note=new Note(title,content,new Date(),imageData);
			
			//System.out.println(note.getId()+"  " + note.getTitle() + " " + note.getContent());
			
			Session s =FactoryProvider.getFactory().openSession();
			
			Transaction tx=s.beginTransaction();
			
			s.save(note);
			
			
			tx.commit();
			
			s.close();
			response.setContentType("text/html");
			PrintWriter out =response.getWriter();
			out.println("<h1 style='text-align:center;'>Note is added successfully</h1>");
			out.println("<h1 style='text-align:center;'><a href='all_notes.jsp'>View all notes</a></h1>");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
