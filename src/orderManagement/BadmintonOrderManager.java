package orderManagement;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.TreeSet;
import java.util.Scanner;
import InputSystem.BadmintonOrderInput;
import OutputSystem.Debuger;
import order.badminton.BadmintonChargeStandards;
import order.badminton.BadmintonOrder;

public class BadmintonOrderManager{
	public BadmintonOrderInput inputHandler;
	public Debuger debuger;
	public HashMap<String, BadmintonOrder>totalOrders;
	public BadmintonChargeStandards badmintonChargeStandards;
	
	public BadmintonOrderManager(){
		initBadmintonOrderManager();
	}
	public void initBadmintonOrderManager(){
		inputHandler = new BadmintonOrderInput();
		debuger = Debuger.getDebuger();
		totalOrders = new HashMap<String, BadmintonOrder>();
		badmintonChargeStandards = new BadmintonChargeStandards();
	}
	public BadmintonOrderManager(String inputFilename,String outputFileName){
		//TODO
		initBadmintonOrderManager();
		try {
			handleOrdersFromFile(inputFilename);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void handleOrdersFromFile (String fileName) throws IOException{
		
		ArrayList<String> orders = inputHandler.loadOrderFromFile(fileName);
		for(String order: orders){
			handleEachBadmintonOrder(order);
		}
	}
	public void handleEachBadmintonOrder(String order){
		BadmintonOrder badmintonOrder = parseOrder(order);
		if(badmintonOrder == null){
//			System.out.println("Error: the booking is invalid!");
			debuger.printDebugInto("Error: the booking is invalid!");
			return;
		}
		if(!badmintonOrder.isCancelOrder()){
			handleBadmintonOrder(badmintonOrder);
		}
		else{
			handleBadmintonCancelOrder(badmintonOrder);
		}
	}
	
	public BadmintonOrder parseOrder(String order){
		//case1 valid order: U123 2016-06-02 20:00~22:00 A
		//case1 valid cancel order: U123 2016-06-02 20:00~22:00 A C
		String[] orderList = order.split(" ");
		if(orderList.length < 4 || orderList.length > 5){
//			String info = "order length not valid:" + orderList.length; 
//			debuger.printDebugInto(info);
			return null;
		}
		String userId = orderList[0];
		//year-month-day check
		String dateString = orderList[1];
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		int weekDay = 0;
		boolean dateValidFlag = true;
		try{
			Date date = format.parse(dateString);
			Calendar cal = Calendar.getInstance();
		    cal.setTime(date);
		    weekDay = cal.get(Calendar.DAY_OF_WEEK) - 1;
		}
		catch(ParseException e){
			dateValidFlag = false;
		}finally{
			if(!dateValidFlag){
//				String info = "order date not valid:" + dateString; 
//				debuger.printDebugInto(info);
				return null;
			}
		}
		//date and time check
		String timePeriod = orderList[2];
		String[] timePeriodList = timePeriod.split("~");
		if(timePeriodList.length != 2){
//			String info = "order date time not valid:" + timePeriod; 
//			debuger.printDebugInto(info);
			return null;
		}
		String startTime = timePeriodList[0];
		String[] startTimeList = startTime.split(":");
		if(startTimeList.length != 2){
			return null;
		}
		int startHour = Integer.parseInt(startTimeList[0]);
		int startMin = Integer.parseInt(startTimeList[1]);
		if(startHour < 0 || startHour > 24 || startMin != 0){
			return null;
		}
		String endTime = timePeriodList[1];
		String[] endTimeList = endTime.split(":");
		if(endTimeList.length != 2){
			return null;
		}
		int endHour = Integer.parseInt(endTimeList[0]);
		int endMin = Integer.parseInt(endTimeList[1]);
		if(endHour < 0 || endHour > 24 || endMin != 0){
			return null;
		}
		if(startHour >= endHour){
			return null;
		}
		// check Badminton place
		String orderPlace = orderList[3];
		if(!badmintonChargeStandards.getBadmintonPlacesResources().containsKey(orderPlace)){
//			String info = "order Badminton place not valid:" + orderPlace; 
//			debuger.printDebugInto(info);
			return null;
		}
		//cancel check
		boolean isCancelOrder = false;
		if(orderList.length == 5){
			if(!orderList[4].equals("C")){
//				String info = "cancel order not valid:" + orderList[4]; 
//				debuger.printDebugInto(info);
				return null;
			}
			else{
				isCancelOrder = true;
			}
		}
		BadmintonOrder badmintonOrder = new BadmintonOrder(order,userId,dateString,startHour,endHour,orderPlace,weekDay);
		badmintonOrder.setCancelOrder(isCancelOrder);
		badmintonOrder.setOrderCharge(badmintonChargeStandards.chargeWithOrder(badmintonOrder));
		return badmintonOrder;
	}
	public void handleBadmintonOrder(BadmintonOrder badmintonOrder){
		boolean result = badmintonChargeStandards.makeBadmintonOrder(badmintonOrder);
		if(result){
//			System.out.println("Success: the booking is accepted!");
			debuger.printDebugInto("Success: the booking is accepted!");
			String orderKey = badmintonOrder.getUserId() + " "+ badmintonOrder.getOrderDate() + " " + badmintonOrder.getOrderStartTime() + " " + badmintonOrder.getOrderEndTime();
			totalOrders.put(orderKey, badmintonOrder);
		}
		else{
//			System.out.println("Error: the booking conflicts with existing bookings!");
			debuger.printDebugInto("Error: the booking conflicts with existing bookings!");
			return;
		}
	}
	public void handleBadmintonCancelOrder(BadmintonOrder badmintonOrder){
		String orderKey = badmintonOrder.getUserId() + " "+ badmintonOrder.getOrderDate() + " " + badmintonOrder.getOrderStartTime() + " " + badmintonOrder.getOrderEndTime();
		boolean result = true;
		if(totalOrders.containsKey(orderKey)){
			BadmintonOrder orignalOrder = totalOrders.get(orderKey);
			if(!orignalOrder.isCancelOrder()){
				result = badmintonChargeStandards.cancelBadmintonOrder(badmintonOrder);
			}
			else{
				//TODO cancel order summit twice
				result = false;
//				System.out.println("Error: the booking being cancelled is repeated!");
				debuger.printDebugInto("Error: the booking being cancelled is repeated!");
				return;
			}
		}
		if(result){
			totalOrders.remove(orderKey);
			totalOrders.put(orderKey,badmintonOrder);
//			System.out.println("Success: the booking is accepted!");
			debuger.printDebugInto("Success: the booking is accepted!");
		}
		else{
//			System.out.println("Error: the booking being cancelled does not exist!");
			debuger.printDebugInto("Error: the booking being cancelled does not exist!");
		}
	}
	public void printTotalIncome(){
//		System.out.println("收入汇总");
//		System.out.println("---");
		debuger.printDebugInto("收入汇总");
		debuger.printDebugInto("---");
		TreeSet<BadmintonOrder> set = new TreeSet<BadmintonOrder>();
		set.addAll(totalOrders.values());
		int totalSum = 0;
		int AllPlacesCount = badmintonChargeStandards.getBadmintonPlaces().length;
		int curCount = 0;
		for(String place : badmintonChargeStandards.getBadmintonPlaces()){
			curCount++;
			String pp = "场地:" + place;
//			System.out.println(pp);
			debuger.printDebugInto(pp);
			int curSum = 0;
			for(BadmintonOrder order : set){
				if(order.getOrderPlace().equals(place)){
					String[] orderList = order.getOriginalOrder().split(" ");
					StringBuffer info = new StringBuffer();
					info.append(orderList[1]);
					info.append(" ");
					info.append(orderList[1]);
					info.append(" ");
					if(order.isCancelOrder()){
						info.append("违约金 ");
					}
					info.append((int)order.getOrderCharge());
					info.append("元");
//					System.out.println(info.toString());
					debuger.printDebugInto(info.toString());
					curSum = curSum + (int)(order.getOrderCharge());
				}
			}
//			System.out.println("小计:" + curSum + "元");
			debuger.printDebugInto("小计:" + curSum + "元");
			if(curCount != AllPlacesCount){
//				System.out.println();
				debuger.printDebugInto("");
			}
			else{
//				System.out.println("---");
				debuger.printDebugInto("---");
			}
			totalSum += curSum;
		}
//		System.out.println("总计:" + totalSum + "元");
		debuger.printDebugInto("总计:" + totalSum + "元");
	}
}
