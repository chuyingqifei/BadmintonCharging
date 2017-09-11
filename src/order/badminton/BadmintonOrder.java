package order.badminton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import order.Order;

public class BadmintonOrder extends Order implements Comparable<BadmintonOrder>{

	private String orderDate;
	private int orderStartTime;
	private int orderEndTime;
	private String orderPlace;
	private int weekDay = 0;
	private double orderCharge = 0;
	private boolean isCancelOrder = false;

	
	public BadmintonOrder(){
		super();
	}
	
	
	public BadmintonOrder(String originalOrder, String userId, String orderDate, int orderStartTime, int orderEndTime, String orderPlace,int weekDay) {
		super(originalOrder, userId);
		this.orderDate = orderDate;
		this.orderStartTime = orderStartTime;
		this.orderEndTime = orderEndTime;
		this.orderPlace = orderPlace;
		this.weekDay = weekDay;
	}


	@SuppressWarnings("finally")
	@Override
	public int compareTo(BadmintonOrder o) {
		// TODO Auto-generated method stub
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int result = 0;
		try {
			Date myDate = format.parse(orderDate);
			Date otherDate = format.parse(o.getOrderDate());
			int ret = myDate.compareTo(otherDate);
			if(ret != 0){
				result = ret;
				return result;
			}
			else{
				if(orderStartTime != o.getOrderStartTime()){
					result = (orderStartTime - o.getOrderStartTime() > 0 ? 1:-1);
				}
				else{
					result = (orderEndTime - o.getOrderEndTime() > 0 ? 1:-1);
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			return result;
		}
	}

	@Override
	public String toString() {
		return "BadmintonOrder [userId=" + userId + ", orderDate=" + orderDate + ", orderStartTime=" + orderStartTime
				+ ", orderEndTime=" + orderEndTime + ", orderPlace=" + orderPlace + ", weekDay=" + weekDay
				+ ", orderCharge=" + orderCharge + ", isCancelOrder=" + isCancelOrder + "]";
	}

	public double getOrderCharge() {
		return orderCharge;
	}


	public void setOrderCharge(double orderCharge) {
		this.orderCharge = orderCharge;
	}


	public boolean isCancelOrder() {
		return isCancelOrder;
	}


	public void setCancelOrder(boolean isCancelOrder) {
		this.isCancelOrder = isCancelOrder;
	}


	public String getUserId() {
		return userId;
	}


	public String getOrderDate() {
		return orderDate;
	}


	public int getOrderStartTime() {
		return orderStartTime;
	}


	public int getOrderEndTime() {
		return orderEndTime;
	}


	public String getOrderPlace() {
		return orderPlace;
	}

	public int getWeekDay() {
		return weekDay;
	}



}
