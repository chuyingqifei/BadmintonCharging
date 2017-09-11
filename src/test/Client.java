package test;

import java.io.IOException;
import java.util.Scanner;

import orderManagement.BadmintonOrderManager;

public class Client {

	public static void main(String[] args){
		// TODO Auto-generated method stub

//		testBadmintonChargingFromInputFile("inputCase1.txt");
		testBadmintonChargingFromInputFile("inputCase2.txt");
//		testBadmintonChargingFromStandardInput();
	}
	
	public static void testBadmintonChargingFromInputFile(String fileName){
		BadmintonOrderManager manager = new BadmintonOrderManager();
		try {
			manager.handleOrdersFromFile(fileName);
		} catch (IOException e) {
			// TODO Auto-generated c atch block
			e.printStackTrace();
			return;
		}
		manager.printTotalIncome();
	}
	
	public static void testBadmintonChargingFromStandardInput(){
		BadmintonOrderManager manager = new BadmintonOrderManager();
		Scanner sc = new Scanner(System.in);
		do{
			String order = sc.nextLine();
			if(order.length() == 0 || order.equals("")){
				break;
			}
			manager.handleEachBadmintonOrder(order);
		}while(true);
		manager.printTotalIncome();
	}	
}
