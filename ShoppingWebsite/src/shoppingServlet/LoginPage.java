package shoppingServlet;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import java.sql.*;
import java.io.*;

//@WebServlet (name="LoginPage",urlPatterns="/login")
public class LoginPage extends HttpServlet {
	
	static Connection con;
	private static String url=null;
	private static String user= null;
	private static String Driver= null;
	private static String Password= null;
	 ResultSet rs;
	 PreparedStatement ps;
	
	@Override
	public void init(ServletConfig config) throws ServletException 
	{
		super.init(config);
		ServletContext context= getServletContext();
		url = context.getInitParameter("url");
		Driver= context.getInitParameter("Driver");
		user = context.getInitParameter("user");
		Password = context.getInitParameter("password");
		
		
		
		//String urlUpdate="jdbc:mysql://localhost:3306:/web_app";
		//System.out.println("Url:"+url+"user :"+user +"passs  "+Password);
		
		try {
			
			Class.forName(Driver);
			
			   con= DriverManager.getConnection(url,user,Password);
			
			if(con!=null)
				System.out.println("Connection success..");
			else
				System.out.println("Connection not success..");
			
		} 
		catch (ClassNotFoundException e) {
			
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
			
		}
		
	}

		@Override
	 protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException
	 {
		 response.setContentType("text/html");
		 PrintWriter out= response.getWriter();
		 String userId= request.getParameter("user");
		 String Password = request.getParameter("Password");
			HttpSession session  =  request.getSession();
			session.setAttribute("JdbcConnection", con);
		 
		 try {
			 ps= con.prepareStatement("select * from web_app.userid_pass where User_id =? and password=?");
				ps.setString(1, userId);
				ps.setString(2,Password);
			
			/*ps= con.prepareStatement("INSERT INTO `web_app`.`customer_info` (customer_id,first_name,last_name,mail_id) VALUES (?,?,?,?)");
			ps.setInt(1,505);
			ps.setString(2,"vishal");
			ps.setString(3,"ankam");
			ps.setString(4,"vishal@gmail.com");*/
			
			 rs = ps.executeQuery(); 		 
			 if (rs.next())
			 {
				 ServletContext ctx=getServletContext();
				 ctx.setAttribute("K","ggggg");
				 
			
		
				 session.setAttribute("Loginuser", userId);
				 System.out.println(userId);
				 
				 out.print("Login Success<br>");
				
					
		     	response.sendRedirect("catagory1.html");
					
			 }
			 else 
			 {
				
				//response.sendRedirect("index.html");
				 String str="<html>";
				 str+="<a href='index.html'>Login</a>"+"</html>";
				 out.print("Login Failed");
				
			 }
			 
			 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		   
	 }
	
	
	 @Override
		public void destroy() 
		{
			try 
			{
				if(con!=null)
					con.close();
				if (rs!=null)
					rs.close();
				if(ps!=null)
					ps.close();
			} 
			catch (SQLException e) 
			{
				e.printStackTrace();
			}
		}
	 
	
}
