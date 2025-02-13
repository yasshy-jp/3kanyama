package bean;

// 在庫インスタンス（在庫管理用）
public class Stock implements java.io.Serializable{
	private int id;  //商品ID
	private String kategoryName;  //品目
	private String name;  //商品名
	private int price;  //単価
	private int stock;  //在庫

	public int getId() {
		return id;
	}
	public String getKategoryName() {
		return kategoryName;
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
	public void setKategoryName(String kategoryName) {
		this.kategoryName=kategoryName;
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
