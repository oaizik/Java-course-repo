package com.nerd.hello;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * Currency Class holds all the Currency within NodeList
 * and making all the arithmathic here.
 * @author Yaron Daya
 *
 */
public class Currency {
	private static NodeList rateList, countryList,countryCode, countryUnit;
	private static int length;
	private static String from=null;
	private static String to=null;
	private static String amount = null;
	

	public static void setTextBoxs(String from, String to,String amount){
		Currency.from = from;
		Currency.to = to;
		Currency.amount = amount;	
	}
	public static void setLength(){
		length = countryList.getLength();
	}
	public static int getLength(){
		return length;
	}
	
	
	public static NodeList getRateList() {
		return rateList;
	}

	public static void setRateList(NodeList rateList) {
		Currency.rateList = rateList;
	}

	public static NodeList getCountryList() {
		return countryList;
	}

	public static void setCountryList(NodeList countryList) {
		Currency.countryList = countryList;
		setLength();
	}

	public static NodeList getCountryCode() {
		return countryCode;
	}

	public static void setCountryCode(NodeList countryCode) {
		Currency.countryCode = countryCode;
	}

	public static NodeList getCountryUnit() {
		return countryUnit;
	}

	public static void setCountryUnit(NodeList countryUnit) {
		Currency.countryUnit = countryUnit;
	}
	
	public static String getLast(NodeList list, int index){
		return list.item(index).getLastChild().getNodeValue();
	}
	
	/**
	 * Calculate function validate the Strings that are in the 
	 * the TextFields and than use Formula to 
	 * calculate the change rate.
	 * @return 
	 */
	public static String calculate(){
		Log.logIt("calculate the given values.");
		if (valid()==true){
		float amountInt = Integer.parseInt(amount);
		int fromCoin = searchAtCode(from);
		int toCoin = searchAtCode(to);
		float outCoin =0;
		float inCoin =0;
		float Score=0;
		//from shekel - work
		if(fromCoin ==-2){
			String doubleToCoin = getLast(rateList,toCoin);//.item(toCoin).getLastChild().getNodeValue();
			int toUnit = Integer.parseInt(getLast(countryUnit,toCoin));//.getLastChild().getNodeValue());
			 outCoin = Float.valueOf(doubleToCoin);
			 inCoin = 1;
			 Score=(amountInt*toUnit)/outCoin;
			
		}
		//to shekel
		else if(toCoin ==-2){
			String doubleFromCoin = getLast(rateList,fromCoin);//rate.item(fromCoin).getLastChild().getNodeValue();
			int fromUnit = Integer.parseInt(getLast(countryUnit,fromCoin));//.item(fromCoin).getLastChild().getNodeValue());
			 inCoin = Float.valueOf(doubleFromCoin);
			 outCoin = 1;
			 Score=(amountInt*inCoin)/fromUnit;
		}
		else{
		String doubleFromCoin = getLast(rateList, fromCoin);//.item(fromCoin).getLastChild().getNodeValue();
		String doubleToCoin = getLast(rateList,toCoin);//rate.item(toCoin).getLastChild().getNodeValue();
		int fromUnit = Integer.parseInt(getLast(countryUnit,fromCoin));//.getLastChild().getNodeValue());
		int toUnit = Integer.parseInt(getLast(countryUnit,toCoin));//.getLastChild().getNodeValue());
		 inCoin = Float.valueOf(doubleFromCoin);
		 outCoin = Float.valueOf(doubleToCoin);
		Score = ((inCoin/fromUnit)*amountInt)/(outCoin/toUnit);
		}
		
		Log.logIt("The formula that has been used: "
				+ "(inCoin/fromUnit)*amountInt)/(outCoin/toUnit) = "
				+  String.valueOf(Score));
		
		String str = String.valueOf(Score);
		//score.setText(str);
		return str;

		}
		else{
			Log.logIt("Calculate has been fail. probebly inserted wrong values");
			return null;
		}
		}
		

	//check if the input TextBox are same one of the row's cell.
	private static boolean valid(){
		if(searchAtCode(from)!=-1 && 
				(searchAtCode(to))!=-1){
			Log.logIt("TextBox values are Correct!");
			return true;
		}
		Log.logIt("TextBox values are not correct!");
		return false;
	}
	
	/*check if the the country name or country code is correct*/
	private static int searchAtCode(String Code){
		Log.logIt("Search the Country-Code inside the NodeList.");
		for(int i=0;i<Currency.getLength();i++)
			if(Code.equals(getLast(countryCode, i ))){
				return i;
			}
		if(Code.equals("ILS")) return -2;
		return -1;
	}
	public static String getFirst(NodeList list, int index) {
		return list.item(index).getFirstChild().getNodeValue();
	}

	
	

}
