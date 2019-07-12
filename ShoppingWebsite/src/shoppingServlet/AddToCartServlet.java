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

public class AddToCartServlet extends HttpServlet {
	
	static Connection con =null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse responce) throws IOException
	{
		PrintWriter out= responce.getWriter();
		
		HttpSession session  =  request.getSession();
		con = (Connection) session.getAttribute("JdbcConnection");
		if (con!=null)
			System.out.println("con not null in AddServlet");
		else 
			System.out.println("null connection AddServlet");
	String userId=	(String) session.getAttribute("Loginuser");
		int catId = Integer.parseInt(request.getParameter("catId"));
		int ProductId =Integer.parseInt(request.getParameter("ProductId"));
		int price = Integer.parseInt(request.getParameter("price"));
		int quantity =1;
		try {
			ps = con.prepareStatement("INSERT INTO web_app.cart(ProductId,catId, price, quantity, userId) VALUES (?,?,?,?,?)");
			ps.setInt(1,ProductId);
			ps.setInt(2,catId);
			ps.setInt(3,price);
			ps.setInt(4,quantity);
			ps.setString(5,userId);
	
			//System.out.println("%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
			 int rs1=ps.executeUpdate();
				out.print(" <a href= 'index.html'>LogOut<br> ");
			 if (rs1>=0)
			 {
				 out.println("cart Updated Successfully");
				 out.print("<html><body><br> Continue Shopping    <a href='category'> Get Back to Products");
				 out.println("<br> Place Order <a href='ListCart'>View Cart");
			 }
			  ps= con.prepareStatement("UPDATE web_app.producttable SET quantity = quantity-1 WHERE  (ProductId = ?)");
			//  ps.setInt(1, quantity-1);
			//  ps.setInt(2, catId);
			  ps.setInt(1,ProductId);
			  int rs2 = ps.executeUpdate();
			  if (rs2>= 0)
			  {
				  System.out.println("reduced the quantity");
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
