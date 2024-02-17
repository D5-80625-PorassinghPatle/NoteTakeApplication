<%@ page import="java.util.List"%>
<%@ page import="org.hibernate.Query"%>
<%@ page import="com.helper.FactoryProvider"%>
<%@ page import="org.hibernate.Session"%>
<%@ page import="com.entities.*"%>
<%@ page import="java.util.Base64"%>
<!-- Import Base64 class -->

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>All notes: Note Taker</title>
<%@ include file="all_js_css.jsp"%>

</head>
<body>

	<div class="container">
		<%@ include file="navbar.jsp"%>
		<br>
		<h1 class="text-uppercase">All Notes:</h1>


		<div class="row">

			<div class="col-12">

				<%
				Session s = FactoryProvider.getFactory().openSession();
				Query q = s.createQuery("from Note");
				List<Note> list = q.list();
				for (Note note : list) {
					if (note != null && note.getImage() != null) { // Null checks for note and image
						try {
					// Encode the image data as base64 string
					byte[] imageData = note.getImage();
					String base64ImageData = Base64.getEncoder().encodeToString(imageData);

					// Display the image
				%>
				<div class="card mt-3">
					<img src="data:image/jpeg;base64,<%=base64ImageData%>"
						alt="Uploaded Image" class="cardimg" />
					<div class="card-body px-5">
						<h5 class="card-title"><%=note.getTitle()%></h5>
						<p class="card-text">
							<%=note.getContent()%>
						</p>
						<p>
							<b class="text-primary"><%=note.getAddedDate()%></b>
						</p>
						<div class="container text-center mt-2">
							<a href="DeleteServlet?note_id=<%=note.getId()%>"
								class="btn btn-danger">Delete</a> <a
								href="edit.jsp?note_id=<%=note.getId()%>"
								class="btn btn-primary">Update</a>
						</div>
					</div>
				</div>
				<%
				} catch (Exception e) {
				out.println("Error displaying image: " + e.getMessage());
				e.printStackTrace();
				}
				} // End of null check
				}

				s.close();
				%>
			</div>

		</div>


	</div>
</body>
</html>
