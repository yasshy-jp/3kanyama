package bean;

//商品インスタンス。（ 商品情報 ）
public class Product implements java.io.Serializable{
	private int id;  //商品ID
	private int kategoryId;  //品目ID
	private String name;  //商品名
	private int price;  //単価
	private int stock;  //在庫

	public int getId() {
		return id;
	}
	public int getKategoryId() {
		return kategoryId;
	}
	public String getName() {
		return name;
	}
	public int getPrice() {
		return price;
	}
	public int getStock() {
		return stock;
	}
	
	public void setId(int id) {
		this.id=id;
	}
	public void setKategoryId(int kategoryId) {
		this.kategoryId=kategoryId;
	}
	public void setName(String name) {
		this.name=name;
	}
	public void setPrice(int price) {
		this.price=price;
	}
	public void setStock(int stock) {
		this.stock=stock;
	}
}
