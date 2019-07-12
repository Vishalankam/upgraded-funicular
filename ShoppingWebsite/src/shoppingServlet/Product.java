package shoppingServlet;

public class Product {

	private int productId;
	private int price;
	private int quantity;

	public Product (){} // constructor 
	
	public Product (int pId, int pri, int qut) //constructor
	{
		productId = pId;
		price = pri;
		quantity = qut;
		
	}
	public int getProductId() {
		return productId;
	}
	public void setProductId(int productId) {
		this.productId = productId;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
