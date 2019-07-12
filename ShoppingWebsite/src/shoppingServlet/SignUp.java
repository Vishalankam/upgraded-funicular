package shoppingServlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.*;
import java.io.*;

/**
 * Servlet implementation class SignUp
 */
// @WebServlet(description = "to register a new client info", urlPatterns = {
// "/SignUp" })

public class SignUp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static Connection con;
	private static String url = null;
	private static String user = null;
	private static String Driver = null;
	private static String Password = null;
	PreparedStatement ps;

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
		ServletContext context = getServletContext();
		url = context.getInitParameter("url");
		Driver = context.getInitParameter("Driver");
		user = context.getInitParameter("user");
		Password = context.getInitParameter("password");
		try {
			Class.forName(Driver);
			con = DriverManager.getConnection(url, user, Password);
			if (con != null)
				System.out.println("Connection success..");
			else
				System.out.println("Connection not success..");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();

		}

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String fname = request.getParameter("name");

		System.out.println("Name " + fname);

		if (fname == " ") {
			System.out.println("name Is Null");
		}

		String name = null;
		String lastName = null;
		String email = null;
		String userId = null;
		String Password = null;
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		userId = request.getParameter("user");
		Password = request.getParameter("Password");
		name = request.getParameter("name");
		
		
		System.out.println(name);
		System.out.println("@@@@@@@@@@@@@@@@@");

		lastName = request.getParameter("lastname");
		email = request.getParameter("email");

		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
		System.out.println(name);
		System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");

		if (name == " " || lastName == " " || email == " " || userId == null
				|| Password == null) {

			System.out.println("null values");
			//response.sendRedirect("signUpForm.html");
			out.print("field missing...  <br> Please Re-Enter the data <a href ='signUpForm.html'>fill the details again");
		}

		else {
			try {
				System.out.println("Executed ...else Part" + name);
				ps = con.prepareStatement("INSERT INTO web_app.customer_info (first_name,last_name,mail_id) VALUES (?,?,?)");
				//ps.setInt(1, 112);
				ps.setString(1, name);
				ps.setString(2, lastName);
				ps.setString(3, email);
				ps.executeUpdate();

				PreparedStatement p = con.prepareStatement(" INSERT INTO  web_app.userid_pass (User_id, password) VALUES (?,?)");
				p.setString(1, userId);
				p.setString(2, Password);
				System.out.println("enterd the login vlaues");
				p.executeUpdate();
			} catch (Exception e) {
				System.out.println("failed to add");
				e.printStackTrace();
			}

			 
			out.print("Registerd Successfully...!");
			
			response.sendRedirect("index.html");
		}
	}
}
