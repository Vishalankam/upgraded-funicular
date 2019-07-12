package shoppingServlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CategoryServlet extends HttpServlet{
	static Connection con =null;
	PreparedStatement ps=null;
	ResultSet rs=null;
   
	
public void doGet(HttpServletRequest request, HttpServletResponse responce) throws IOException
{
	PrintWriter out= responce.getWriter();
	
	HttpSession session  =  request.getSession();
	con = (Connection) session.getAttribute("JdbcConnection");
	if (con!=null)
		System.out.println("con not null in category");
	else 
		System.out.println("null connection ");
    out.println("welcome "+session.getAttribute("Loginuser"));
    
    out.println("<html><br><body>");
	out.println("<table border=1>");
	out.println("<th>Cat Id</th><th>Cat Name</th>");
	
	try {
		 ps=con.prepareStatement("select * from web_app.producttable");
		rs = ps.executeQuery();
		out.print(" <a href= 'index.html'>LogOut<br> ");
		int catId;
		String catName ;
		
		while (rs.next())
		{
		catId= 	rs.getInt("catId");
	
		catName=rs.getString("catName");
		out.println("<tr><td>"+catId+"</td><td>"+catName+"</td><a href='Product?catId="+catId+">");
 	out.println("<img src='images/'width='70' height='50'></img></a></td></tr>");
		//out.println("</a></td></tr>");
		
		}
		out.print("<form action='product' method='get'>");
		out.print("Enter catId: <input type='text' name='catId'><br>");
		out.print("<br> &nbsp;&nbsp;&nbsp; <input type='submit' value='submit'>");
		out.print("</form>");		
		out.println("</table>");
		out.println("</body></html>");
		out.println("<br>Go To Cart<a href='ListCart'>View Cart</a>");
		
	} catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	
	
	
	}

}
