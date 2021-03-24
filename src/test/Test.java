package test;


import dev.ronaldomarques.myutility.MyUtility;
import dev.ronaldomarques.myutility.screenprinter.SP;



public final class Test {
	
	public static void main(String[] args) {
		
		System.out.println("teste lib " + MyUtility.libraryName());
		
		SP.pdln("teste pd 1");
		
		SP.pdOff();
		
		SP.pdln("teste pd 2");
		
		SP.pdOn();
		
		SP.pd("teste pd 3");
	}
	
}
