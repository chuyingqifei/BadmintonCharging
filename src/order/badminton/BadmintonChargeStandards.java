package order.badminton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

import OutputSystem.Debuger;
import order.ChargeStandards;
import order.Order;

public class BadmintonChargeStandards implements ChargeStandards {

	private String[] badmintonPlaces;
	
	private HashMap<String, HashSet<String>> badmintonPlacesResources;
	private Debuger debuger;
	public BadmintonChargeStandards(){
		debuger = Debuger.getDebuger();
		badmintonPlaces = new String[]{"A","B","C","D"};
		badmintonPlacesResources = new HashMap<String, HashSet<String>>();
		for(String palce : badmintonPlaces){
			badmintonPlacesResources.put(palce, new HashSet<String>());
		}
	}
	
	public double chargeWithOrder(Order order){
		BadmintonOrder bOrder = (BadmintonOrder)order;
		double charge = chargeWithOrder(bOrder.getOrderPlace(),bOrder.getWeekDay(),bOrder.getOrderStartTime(),bOrder.getOrderEndTime(),bOrder.isCancelOrder());
		return charge;
	}
	
	public double chargeWithOrder(String place,int weekDay,int startHour,int endHour,boolean cancelOrder){
		double charge = 0;
		for(int curTime = startHour;curTime < endHour;curTime++){
			charge += chargeEveryHour(place,weekDay,curTime);
		}
		if(cancelOrder){
			if(weekDay <= 5 && weekDay >= 1){
				charge = charge * 0.5;
			}
			else{
				charge = charge * 0.25;
			}
		}
		return charge;
	}
	
	public int chargeEveryHour(String place,int weekDay,int curTime){
		if(weekDay <= 5 && weekDay >= 1){
			if(curTime >= 9 && curTime < 12){
				return 30;
			}
			else if(curTime >= 12 && curTime < 18){
				return 50;
			}
			else if(curTime >= 18 && curTime < 20){
				return 80;
			}
			else if(curTime >= 20 && curTime < 22){
				return 60;
			}
			else{
				return 0;
			}
		}
		else{
			if(curTime >= 9 && curTime < 12){
				return 40;
			}
			else if(curTime >= 12 && curTime < 18){
				return 50;
			}
			else if(curTime >= 18 && curTime < 22){
				return 60;
			}
			else{
				return 0;
			}
		}
	}
	
	public String[] getBadmintonPlaces() {
		return badmintonPlaces;
	}
	
	public HashMap<String, HashSet<String>> getBadmintonPlacesResources() {
		return badmintonPlacesResources;
	}
	
	public boolean makeBadmintonOrder(BadmintonOrder order){
		boolean executeState = true;
		boolean resourceAvailble = true;
		int startHour = order.getOrderStartTime();
		int endHour = order.getOrderEndTime();
		HashSet<String> curResources = badmintonPlacesResources.get(order.getOrderPlace());
		ArrayList<String> orderResources = new ArrayList<String>();
		for(int curTime = startHour;curTime < endHour;curTime++){
			String temp = order.getOrderDate()+":" + curTime;
			if(curResources.contains(temp)){
				resourceAvailble = false;
				break;
			}
			else{
				orderResources.add(temp);
			}
		}
		if(resourceAvailble){
			for(int i = 0;i<orderResources.size();i++){
				curResources.add(orderResources.get(i));
			}
		}
		else{
			executeState = false;
//			String info = "makeBadmintonOrder failed resources uesd already:" +  order.toString();
//			debuger.printDebugInto(info);
		}
		orderResources.clear();
		return executeState;
	}
	
	public boolean cancelBadmintonOrder(BadmintonOrder order){
		boolean executeState = true;
		boolean resourceExist = true;
		int startHour = order.getOrderStartTime();
		int endHour = order.getOrderEndTime();
		HashSet<String> curResources = badmintonPlacesResources.get(order.getOrderPlace());
		ArrayList<String> removeOrderResources = new ArrayList<String>();
		for(int curTime = startHour;curTime < endHour;curTime++){
			String temp = order.getOrderDate()+":" + curTime;
			if(!curResources.contains(temp)){
				resourceExist = false;
				break;
			}
			else{
				removeOrderResources.add(temp);
			}
		}
		if(resourceExist){
			for(int i = 0;i<removeOrderResources.size();i++){
				curResources.remove(removeOrderResources.get(i));
			}
		}
		else{
			executeState = false;
//			String info = "cancelBadmintonOrder failed resources not uesd :" +  order.toString();
//			debuger.printDebugInto(info);
		}
		removeOrderResources.clear();
		return executeState;
	}
}
