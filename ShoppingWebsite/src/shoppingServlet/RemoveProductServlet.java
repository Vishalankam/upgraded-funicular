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

public class RemoveProductServlet extends HttpServlet {
	static Connection con =null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse responce) throws IOException
	{
		PrintWriter out= responce.getWriter();
		
		HttpSession session  =  request.getSession();
		con = (Connection) session.getAttribute("JdbcConnection");
		if (con!=null)
			System.out.println("con not null in RemoveProduct");
		else 
			System.out.println("null connection RemoveProduct");
		int no = Integer.parseInt(request.getParameter("no"));
		int ProductId = Integer.parseInt(request.getParameter("ProductId"));
		
		try {
			ps= con.prepareStatement("DELETE FROM web_app.cart WHERE (no = ?)");
			ps.setInt(1, no);
			int r=ps.executeUpdate();
			if (r>=0)
			ps = con.prepareStatement("UPDATE web_app.producttable SET quantity =quantity+1 WHERE  (ProductId = ?)");
			ps.setInt(1, ProductId);
			ps.executeUpdate();
			out.print(" <a href= 'index.html'>LogOut <br>");
			System.out.println("product ADDED(remove Product servlet)");
			out.print("removed the product <a href= 'ListCart'>back to cart");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		

}
}
