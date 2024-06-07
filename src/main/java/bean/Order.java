package bean;

// 注文インスタンス。（ 注文情報 ）
public class Order implements java.io.Serializable {
	private int proceeds_id;
	private String date; // 購入日
	private String simei; // お客様氏名
	private String charge_id; // 決済ID
	private int totalprice; // 合計金額
	private String name; // 商品名
	private int price; // 単価
	private int count; // 個数

	// ゲッタ
	public int getId() {
		return proceeds_id;
	}

	public String getDate() {
		return date;
	}

	public String getSimei() {
		return simei;
	}

	public String getCharge_id() {
		return charge_id;
	}

	public int getTotalprice() {
		return totalprice;
	}

	public String getName() {
		return name;
	}

	public int getPrice() {
		return price;
	}

	public int getCount() {
		return count;
	}

	// セッタ
	public void setId(int id) {
		this.proceeds_id = id;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public void setSimei(String simei) {
		this.simei = simei;
	}

	public void setCharge_id(String charge_id) {
		this.charge_id = charge_id;
	}

	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
