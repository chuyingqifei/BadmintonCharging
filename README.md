# BadmintonCharging 作业运行说明
代码说明：题目的设计基于可扩展的架构进行设计，可扩展的方向包括：足球场、篮球场等预订的订单管理。基本满足了开放封闭原则。
涉及的类及其功能包括：
	基类：order.Order, 用于对球场的预订订单和取消订单进行描述
	子类：order.badminton.BadmintonOrder， 用于对羽毛球球场预订订单和取消订单进行描述
	基类：order.ChargeStandards，用于对球场的收费情况进行描述
	子类：order.badminton.BadmintonChargeStandards，用于对羽毛球球场的收费情况进行描述
	BadmintonOrderManager采用组合模式设计思想，组合的类包括:BadmintonOrderInput,BadmintonChargeStandards以及Debuger。
	其中，BadmintonOrderInput处理订单的输入，BadmintonChargeStandards负责订单收费的计算，Debuger负责程序的打印输出以及日志记录。
	Debuger设置了日志的开关功能，可根据配置来打开或关闭debug信息。

数据结构说明：
	BadmintonChargeStandards中的HashMap<String, HashSet<String>> badmintonPlacesResources,用来对四块羽毛球球场资源进行管理。
	其中，内部的HashSet<String>>用来对具体某一块球场（例如A球场）的预约时间进行管理，其时间管理的粒度为1h。
	eg, 2016-08-01:20 代表 2016年8月1日20点时间已被预约。
	
测试程序见：test.Client.java
	支持两种测试方式：
	1. 从文件读取测试用例
		public static void testBadmintonChargingFromInputFile(String fileName);
	2. 从标准输入读取测试用例
		public static void testBadmintonChargingFromStandardInput();




