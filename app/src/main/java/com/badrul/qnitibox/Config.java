package com.badrul.qnitibox;

public class Config {

	public static final String SHARED_PREF_NAME = "jimatBox";

	public static final String MENU_TYPE = "menuType";
	public static final String MENU_DAY = "menuDay";
	public static final String ORDER_DATE = "orderDate";
	public static final String ORDER_TIME = "orderTime";


	public static final String ORDER_STATUS_COMPLETE = "http://zakatkedah.cvmall.my/loginmanager.php";

	//FOR ORDER
	public static final String ORDER_ID = "orderid";
	public static final String CARD_ID = "cardID";
	public static final String NAME_ID = "nameID";
	public static final String PHONE_ID = "phoneID";
	public static final String EMAIL_ID = "emailID";
	public static final String MATRIX_ID = "matrixID";
	public static final String ORDER_TYPE = "orderType";
	public static final String ORDER_DAY = "orderDay";
	public static final String ORDER_DATE2 = "orderDate2";
	public static final String ORDER_TIME2 = "orderTime2";
	public static final String ORDER_QTT = "orderQTT";
	public static final String PICKUP_LOCATION = "puLocation";
	public static final String PICKUP_TIME = "puTime";


	public static final String COMPLETE_DATE = "completeDate";
	public static final String COMPLETE_TIME = "completeTime";
	public static final String ORDER_STATUS = "orderStatus";


	public static final String LOGIN_URL = "http://zakatkedah.cvmall.my/loginmanager.php";

	//Keys for email and password as defined in our $_POST['key'] in login.php
//public static final String KEY_ID = "userIC";
	public static final String KEY_PASSWORD = "userPass";

	//If server response is equal to this that means login is successful
	public static final String LOGIN_SUCCESS = "success";

	//This would be used to store the phone number of current logged in user
	public static final String ID_SHARED_PREF = "userID";

	//We will use this to store the boolean in sharedpreference to track user is loggedin or not
	public static final String LOGGEDIN_SHARED_PREF = "loggedin";
}
