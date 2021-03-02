package com.badrul.qnitibox;

public class Config {

	public static final String SHARED_PREF_NAME = "jimatBox";

	public static final String MENU_TYPE = "menuType";
	public static final String MENU_DAY = "menuDay";
	public static final String ORDER_DATE = "orderDate";
	public static final String ORDER_TIME = "orderTime";

	public static final String PUSH_NOTI_DELIVERER = "https://qnitibox.qniti.com/apps/notitodeliverer.php";

	public static final String URL_CHECKDATE = "https://qnitibox.qniti.com/apps/checkdate.php";
	public static final String URL_CHECKMAXQTT = "https://qnitibox.qniti.com/apps/checkmaxqtt.php";
	public static final String URL_CHECKPROMOQTT = "https://qnitibox.qniti.com/apps/getpromoqtt.php?userLocation=";

	public static final String URL_CHECKSALEDATE = "https://qnitibox.qniti.com/apps/getsaletime.php?foodID=";
	public static final String CANCEL_ORDER_URL = "https://qnitibox.qniti.com/apps/admin/cancelorder.php";


	public static final String ORDER_STATUS_CANCEL = "https://qnitibox.qniti.com/apps/orderstatuscancel.php?userID=";
	public static final String ORDER_STATUS_COMPLETE = "https://qnitibox.qniti.com/apps/orderstatuscomplete.php?userID=";
	public static final String ORDER_STATUS_PROCESSING = "https://qnitibox.qniti.com/apps/orderstatusprocessing.php?userID=";
	public static final String PROFILE = "https://qnitibox.qniti.com/apps/getuserinfo.php?userEmail=";
	public static final String SHOW_FOOD = "https://qnitibox.qniti.com/apps/showfood.php?inasisID=";
    public static final String CHECK_USERPROMO= "https://qnitibox.qniti.com/apps/checkifpromo.php";
	public static final String FOOD_ID = "foodID";

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
	public static final String ORDER_COMPLETEDATE = "CompleteDate";
	public static final String ORDER_COMPLETETIME = "CompleteTime";
	public static final String ORDER_QTT = "orderQTT";
	public static final String ORDER_USERTYPE = "orderUserType";
	public static final String PICKUP_LOCATION = "puLocation";
	public static final String PICKUP_TIME = "puTime";
	public static final String FOOD_PRICE = "foodPrice";
	public static final String FOOD_PRICE_ALL = "foodPriceAll";
	public static final String TOTAL_FOOD_PRICE = "totalfoodPrice";
	public static final String ORDER_FOODID = "orderFoodID";
	public static final String ORDER_CANCELMSG = "orderCancelMsg";

	public static final String USER_TOKEN = "userToken";

	public static final String FOOD_DESC = "foodDesc";
	public static final String FOOD_IMAGE = "foodImage";

	public static final String PROMO_ID = "promoID";
	public static final String PROMO_QTT = "promoQTT";

	public static final String SALETIME_ID = "saletimeID";
	public static final String SALETIME_START = "saleStart";
	public static final String SALETIME_END = "saleEnd";

	public static final String INASIS_ID = "inasisID";
	public static final String INASIS_NAME = "inasisName";
	public static final String INASIS_LOCATION = "inasisLocation";
	public static final String INASIS_LOGO = "inasisLogo";

	public static final String COMPLETE_DATE = "completeDate";
	public static final String COMPLETE_TIME = "completeTime";
	public static final String ORDER_STATUS = "orderStatus";

	//For User
	public static final String USER_ID2 = "userID2";
	public static final String NAME_ID2 = "nameID2";
	public static final String PHONE_ID2 = "phoneID2";
	public static final String EMAIL_ID2 = "emailID2";
	public static final String MATRIX_ID2 = "matrixID";
	public static final String LOCATION_ID2 = "userLocation";
	public static final String PROMO = "userPromo";

	public static final String LOGIN_URL = "https://qnitibox.qniti.com/apps/loginqniti.php";
	public static final String REGISTER_URL = "https://qnitibox.qniti.com/apps/registerqniti.php";

	public static final String GET_INASIS = "https://qnitibox.qniti.com/apps/getInasis.php?inasisLocation=";

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
