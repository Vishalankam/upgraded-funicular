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

public class ProductServlet extends HttpServlet {
	
	static Connection con =null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse responce) throws IOException
	{
		PrintWriter out= responce.getWriter();
		
		HttpSession session  =  request.getSession();
		con = (Connection) session.getAttribute("JdbcConnection");
		if (con!=null)
			System.out.println("con not null in Product");
		else 
			System.out.println("null connection Product");
		
	
		int catId = Integer.parseInt(request.getParameter("catId"));
		 out.println("<html><br><body>");
			out.println("<table border=1>");
			out.println("<th>Product Id</th><th>Product Name</th><th>Price</th><th>Quantity</th>");
			
			try {
				 ps=con.prepareStatement("select * from web_app.producttable WHERE catId=?");
				 ps.setInt(1, catId);
				rs = ps.executeQuery();
				out.print(" <a href= 'index.html'>LogOut<br> ");
				
				int ProductId;
				String ProductName ;
				int price ;
				int quantity;
				
				while (rs.next())
				{
					ProductId = rs.getInt("ProductId");
					ProductName = rs.getString("ProductName");
					price = rs.getInt("price");
					quantity = rs.getInt("quantity");
					out.println("<tr><td>"+ProductId+"</td><td>"+ProductName+"</td><td>"+price+"</td><td>"+quantity+"</td>");
					//out.println("<img src='images/'width='70' height='50'></img></a></td></tr>");
					   out.println("<td><a href='AddToCart?ProductId="+ProductId+"&catId="+catId+"&price="+price+"&quantity=1'>Add To Cart</a></td></tr>");
				
					 
				
				}
				
	}
			 catch (SQLException e) {
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
