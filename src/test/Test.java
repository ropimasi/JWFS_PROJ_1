package test;


import dev.ronaldomarques.myutility.MyUtility;
import dev.ronaldomarques.myutility.debugger.DP;



public final class Test {
	
	public static void main(String[] args) {
		
		System.out.println("teste lib " + MyUtility.libraryName());
		
		DP.pdln("teste pd 1");
		
		DP.pdOff();
		
		DP.pdln("teste pd 2");
		
		DP.pdOn();
		
		DP.pdln("teste pd 3");
		
		MyUtility.selfPresentation();
	}
	
}
