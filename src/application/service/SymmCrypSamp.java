package application.service;


/* MERELY DIDACTIC IMPLEMENTATION ABOUT: WHAT IS CRYPTOGRAPHY.
 * It will be replaced soon. Replaced by on of my personal projects,
 * witch is a little bit better coded, it looks like a cryptography
 * package: CryptoCorePack. */

/* Symmetric Cryptography Sample */
public final class SymmCrypSamp {
	
	/* Private class' attributes. */
	private static char switchIt(char c) {
		
		switch (c) {
			case 'a':
				return 'q';
			
			case 'b':
				return 'w';
			
			case 'c':
				return 'e';
			
			case 'd':
				return 'R';
			
			case 'e':
				return '4';
			
			case 'f':
				return 'Y';
			
			case 'g':
				return 'u';
			
			case 'h':
				return 'i';
			
			case 'i':
				return 'o';
			
			case 'j':
				return '8';
			
			case 'k':
				return 'A';
			
			case 'l':
				return 'S';
			
			case 'm':
				return 'd';
			
			case 'n':
				return 'f';
			
			case 'o':
				return '2';
			
			case 'p':
				return 'H';
			
			case 'q':
				return 'J';
			
			case 'r':
				return 'K';
			
			case 's':
				return 'l';
			
			case 't':
				return '6';
			
			case 'u':
				return 'x';
			
			case 'v':
				return 'C';
			
			case 'w':
				return 'V';
			
			case 'x':
				return 'B';
			
			case 'y':
				return '0';
			
			case 'z':
				return 'm';
			
			case 'A':
				return 'Q';
			
			case 'B':
				return 'W';
			
			case 'C':
				return '5';
			
			case 'D':
				return 'r';
			
			case 'E':
				return 't';
			
			case 'F':
				return 'y';
			
			case 'G':
				return '9';
			
			case 'H':
				return 'I';
			
			case 'I':
				return 'O';
			
			case 'J':
				return 'p';
			
			case 'K':
				return 'a';
			
			case 'L':
				return '3';
			
			case 'M':
				return 'D';
			
			case 'N':
				return 'F';
			
			case 'O':
				return 'G';
			
			case 'P':
				return 'h';
			
			case 'Q':
				return 'j';
			
			case 'R':
				return '7';
			
			case 'S':
				return 'L';
			
			case 'T':
				return 'Z';
			
			case 'U':
				return 'X';
			
			case 'V':
				return 'c';
			
			case 'W':
				return 'v';
			
			case 'X':
				return 'b';
			
			case 'Y':
				return '1';
			
			case 'Z':
				return ' ';
			
			case ' ':
				return 'M';
			
			case '0':
				return 'T';
			
			case '1':
				return 'P';
			
			case '2':
				return 'g';
			
			case '3':
				return 'z';
			
			case '4':
				return 'n';
			
			case '5':
				return 'E';
			
			case '6':
				return 'U';
			
			case '7':
				return 's';
			
			case '8':
				return 'k';
			
			case '9':
				return 'N';
			
			default:
				return c;
		}
		
	}
	
	
	
	private static char unswitchIt(char c) {
		
		switch (c) {
			case 'q':
				return 'a';
			
			case 'w':
				return 'b';
			
			case 'e':
				return 'c';
			
			case 'R':
				return 'd';
			
			case '4':
				return 'e';
			
			case 'Y':
				return 'f';
			
			case 'u':
				return 'g';
			
			case 'i':
				return 'h';
			
			case 'o':
				return 'i';
			
			case '8':
				return 'j';
			
			case 'A':
				return 'k';
			
			case 'S':
				return 'l';
			
			case 'd':
				return 'm';
			
			case 'f':
				return 'n';
			
			case '2':
				return 'o';
			
			case 'H':
				return 'p';
			
			case 'J':
				return 'q';
			
			case 'K':
				return 'r';
			
			case 'l':
				return 's';
			
			case '6':
				return 't';
			
			case 'x':
				return 'u';
			
			case 'C':
				return 'v';
			
			case 'V':
				return 'w';
			
			case 'B':
				return 'x';
			
			case '0':
				return 'y';
			
			case 'm':
				return 'z';
			
			case 'Q':
				return 'A';
			
			case 'W':
				return 'B';
			
			case '5':
				return 'C';
			
			case 'r':
				return 'D';
			
			case 't':
				return 'E';
			
			case 'y':
				return 'F';
			
			case '9':
				return 'G';
			
			case 'I':
				return 'H';
			
			case 'O':
				return 'I';
			
			case 'p':
				return 'J';
			
			case 'a':
				return 'K';
			
			case '3':
				return 'L';
			
			case 'D':
				return 'M';
			
			case 'F':
				return 'N';
			
			case 'G':
				return 'O';
			
			case 'h':
				return 'P';
			
			case 'j':
				return 'Q';
			
			case '7':
				return 'R';
			
			case 'L':
				return 'S';
			
			case 'Z':
				return 'T';
			
			case 'X':
				return 'U';
			
			case 'c':
				return 'V';
			
			case 'v':
				return 'W';
			
			case 'b':
				return 'X';
			
			case '1':
				return 'Y';
			
			case ' ':
				return 'Z';
			
			case 'M':
				return ' ';
			
			case 'T':
				return '0';
			
			case 'P':
				return '1';
			
			case 'g':
				return '2';
			
			case 'z':
				return '3';
			
			case 'n':
				return '4';
			
			case 'E':
				return '5';
			
			case 'U':
				return '6';
			
			case 's':
				return '7';
			
			case 'k':
				return '8';
			
			case 'N':
				return '9';
			
			default:
				return c;
		}
		
	}
	
	
	
	/* Public class' attributes. */
	public static String doIt(String decMsg) {
		
		if ((decMsg.length() % 2) == 0) {
			decMsg = "Ovisk " + decMsg;
		}
		else {
			decMsg = "ovi" + decMsg + " sk";
		}
		
		char[] tmp = decMsg.toCharArray();
		String encMsg = "";
		
		for (int i = 0; i <= decMsg.length() - 1; i++) { encMsg += switchIt(tmp[i]); }
		
		return encMsg;
		
	}
	
	
	
	public static String undoIt(String encMsg) {
		
		if ((encMsg.length() % 2) == 0) {
			encMsg = encMsg.substring(6);
		}
		else {
			encMsg = encMsg.substring(3, encMsg.length() - 3);
		}
		
		char[] tmp = encMsg.toCharArray();
		String decMsg = "";
		
		for (int i = 0; i <= encMsg.length() - 1; i++) { decMsg += unswitchIt(tmp[i]); }
		
		return decMsg;
		
	}
	
}
