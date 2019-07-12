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

@SuppressWarnings("serial")
public class ListCartServlet extends HttpServlet {
	static Connection con =null;
	PreparedStatement ps=null;
	ResultSet rs=null;
	
	public void doGet(HttpServletRequest request, HttpServletResponse responce) throws IOException
	{
		PrintWriter out= responce.getWriter();
		
		HttpSession session  =  request.getSession();
		con = (Connection) session.getAttribute("JdbcConnection");
		if (con!=null)
			System.out.println("con not null in listCart");
		else 
			System.out.println("null connection listCart");
	
		String userId=	(String) session.getAttribute("Loginuser");
        int catId;
        int ProductId;
        int price;
		int quantity =1;
	   int total_qty=0;
	   int total_Price=0;
	   int no;
		 out.println("<html><body>");
		 out.print("welcome Mr."+userId);
			out.println("<table border=1>");
			out.println("<th>cat Id</th><th>Product Id</th><th>Price</th><th>Quantity</th>");
			
		
		try {
			ps = con.prepareStatement("select * from web_app.cart WHERE userId=?");
			ps.setString(1,userId);
			rs= ps.executeQuery();
			out.print(" <a href= 'index.html'>LogOut<br> ");
			
			while (rs.next())
			{
				catId=rs.getInt("catId");
				ProductId=rs.getInt("ProductId");
				price=rs.getInt("price");
				no = rs.getInt("no");
				quantity=rs.getInt("quantity");
				out.println("<tr><td>"+catId+"</td><td>"+ProductId+"</td><td>"+price+"</td><td>"+quantity+"</td> <td><a href='RemoveProduct?no="+no+"&ProductId="+ProductId+"'>Remove product from cart </td><tr>");
				total_qty += quantity;
				total_Price += price;
			}
			out.print("<tr><td>total Price"+total_Price+"</td></tr>");

			out.print("<tr><td>total quantity"+total_qty+"</td></tr>");
			out.print("<br><tr><a href =' PlaceOrder'>Place Order</tr>");
			
			
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
