package order;

public class Order {
	protected String originalOrder;
	protected String userId;
	
	public Order(){
		
	}
	public Order(String originalOrder, String userId){
		this.originalOrder = originalOrder;
		this.userId = userId;
	}

	public String getUserId() {
		return userId;
	}
	
	public String getOriginalOrder() {
		return originalOrder;
	}
	
	
	@Override
	public String toString() {
		return "Order [originalOrder=" + originalOrder + "]";
	}
	
}
