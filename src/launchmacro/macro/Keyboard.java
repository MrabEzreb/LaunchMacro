package launchmacro.macro;
import static java.awt.event.KeyEvent.VK_0;
import static java.awt.event.KeyEvent.VK_1;
import static java.awt.event.KeyEvent.VK_2;
import static java.awt.event.KeyEvent.VK_3;
import static java.awt.event.KeyEvent.VK_4;
import static java.awt.event.KeyEvent.VK_5;
import static java.awt.event.KeyEvent.VK_6;
import static java.awt.event.KeyEvent.VK_7;
import static java.awt.event.KeyEvent.VK_8;
import static java.awt.event.KeyEvent.VK_9;
import static java.awt.event.KeyEvent.VK_A;
import static java.awt.event.KeyEvent.VK_AMPERSAND;
import static java.awt.event.KeyEvent.VK_ASTERISK;
import static java.awt.event.KeyEvent.VK_AT;
import static java.awt.event.KeyEvent.VK_B;
import static java.awt.event.KeyEvent.VK_BACK_QUOTE;
import static java.awt.event.KeyEvent.VK_BACK_SLASH;
import static java.awt.event.KeyEvent.VK_C;
import static java.awt.event.KeyEvent.VK_CIRCUMFLEX;
import static java.awt.event.KeyEvent.VK_CLOSE_BRACKET;
import static java.awt.event.KeyEvent.VK_COLON;
import static java.awt.event.KeyEvent.VK_COMMA;
import static java.awt.event.KeyEvent.VK_D;
import static java.awt.event.KeyEvent.VK_DOLLAR;
import static java.awt.event.KeyEvent.VK_E;
import static java.awt.event.KeyEvent.VK_ENTER;
import static java.awt.event.KeyEvent.VK_EQUALS;
import static java.awt.event.KeyEvent.VK_EXCLAMATION_MARK;
import static java.awt.event.KeyEvent.VK_F;
import static java.awt.event.KeyEvent.VK_G;
import static java.awt.event.KeyEvent.VK_H;
import static java.awt.event.KeyEvent.VK_I;
import static java.awt.event.KeyEvent.VK_J;
import static java.awt.event.KeyEvent.VK_K;
import static java.awt.event.KeyEvent.VK_L;
import static java.awt.event.KeyEvent.VK_LEFT_PARENTHESIS;
import static java.awt.event.KeyEvent.VK_M;
import static java.awt.event.KeyEvent.VK_MINUS;
import static java.awt.event.KeyEvent.VK_N;
import static java.awt.event.KeyEvent.VK_NUMBER_SIGN;
import static java.awt.event.KeyEvent.VK_O;
import static java.awt.event.KeyEvent.VK_OPEN_BRACKET;
import static java.awt.event.KeyEvent.VK_P;
import static java.awt.event.KeyEvent.VK_PERIOD;
import static java.awt.event.KeyEvent.VK_PLUS;
import static java.awt.event.KeyEvent.VK_Q;
import static java.awt.event.KeyEvent.VK_QUOTE;
import static java.awt.event.KeyEvent.VK_QUOTEDBL;
import static java.awt.event.KeyEvent.VK_R;
import static java.awt.event.KeyEvent.VK_RIGHT_PARENTHESIS;
import static java.awt.event.KeyEvent.VK_S;
import static java.awt.event.KeyEvent.VK_SEMICOLON;
import static java.awt.event.KeyEvent.VK_SHIFT;
import static java.awt.event.KeyEvent.VK_SLASH;
import static java.awt.event.KeyEvent.VK_SPACE;
import static java.awt.event.KeyEvent.VK_T;
import static java.awt.event.KeyEvent.VK_TAB;
import static java.awt.event.KeyEvent.VK_U;
import static java.awt.event.KeyEvent.VK_UNDERSCORE;
import static java.awt.event.KeyEvent.VK_V;
import static java.awt.event.KeyEvent.VK_W;
import static java.awt.event.KeyEvent.VK_X;
import static java.awt.event.KeyEvent.VK_Y;
import static java.awt.event.KeyEvent.VK_Z;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;

public class Keyboard {

    private Robot robot;

    public Keyboard() throws AWTException {
        this.robot = new Robot();
    }

    public Keyboard(Robot robot) {
        this.robot = robot;
    }

    public void type(String characters) {
    	String[] commands = characters.split("\f");
    	for (String string : commands) {
    		int command = 0;
    		if(string.charAt(0) == '+') {
    			command = 1;
    		} else if(string.charAt(0) == '-') {
    			command = -1;
    		}
			type(string.substring(1), command);
		}
    }

    public void type(String chars, int command) {
    	if(chars.length() == 1) {
	    	char character = chars.charAt(0);
	        switch (character) {
	        case 'a': doType(command, VK_A); break;
	        case 'b': doType(command, VK_B); break;
	        case 'c': doType(command, VK_C); break;
	        case 'd': doType(command, VK_D); break;
	        case 'e': doType(command, VK_E); break;
	        case 'f': doType(command, VK_F); break;
	        case 'g': doType(command, VK_G); break;
	        case 'h': doType(command, VK_H); break;
	        case 'i': doType(command, VK_I); break;
	        case 'j': doType(command, VK_J); break;
	        case 'k': doType(command, VK_K); break;
	        case 'l': doType(command, VK_L); break;
	        case 'm': doType(command, VK_M); break;
	        case 'n': doType(command, VK_N); break;
	        case 'o': doType(command, VK_O); break;
	        case 'p': doType(command, VK_P); break;
	        case 'q': doType(command, VK_Q); break;
	        case 'r': doType(command, VK_R); break;
	        case 's': doType(command, VK_S); break;
	        case 't': doType(command, VK_T); break;
	        case 'u': doType(command, VK_U); break;
	        case 'v': doType(command, VK_V); break;
	        case 'w': doType(command, VK_W); break;
	        case 'x': doType(command, VK_X); break;
	        case 'y': doType(command, VK_Y); break;
	        case 'z': doType(command, VK_Z); break;
	        case 'A': doType(command, VK_SHIFT, VK_A); break;
	        case 'B': doType(command, VK_SHIFT, VK_B); break;
	        case 'C': doType(command, VK_SHIFT, VK_C); break;
	        case 'D': doType(command, VK_SHIFT, VK_D); break;
	        case 'E': doType(command, VK_SHIFT, VK_E); break;
	        case 'F': doType(command, VK_SHIFT, VK_F); break;
	        case 'G': doType(command, VK_SHIFT, VK_G); break;
	        case 'H': doType(command, VK_SHIFT, VK_H); break;
	        case 'I': doType(command, VK_SHIFT, VK_I); break;
	        case 'J': doType(command, VK_SHIFT, VK_J); break;
	        case 'K': doType(command, VK_SHIFT, VK_K); break;
	        case 'L': doType(command, VK_SHIFT, VK_L); break;
	        case 'M': doType(command, VK_SHIFT, VK_M); break;
	        case 'N': doType(command, VK_SHIFT, VK_N); break;
	        case 'O': doType(command, VK_SHIFT, VK_O); break;
	        case 'P': doType(command, VK_SHIFT, VK_P); break;
	        case 'Q': doType(command, VK_SHIFT, VK_Q); break;
	        case 'R': doType(command, VK_SHIFT, VK_R); break;
	        case 'S': doType(command, VK_SHIFT, VK_S); break;
	        case 'T': doType(command, VK_SHIFT, VK_T); break;
	        case 'U': doType(command, VK_SHIFT, VK_U); break;
	        case 'V': doType(command, VK_SHIFT, VK_V); break;
	        case 'W': doType(command, VK_SHIFT, VK_W); break;
	        case 'X': doType(command, VK_SHIFT, VK_X); break;
	        case 'Y': doType(command, VK_SHIFT, VK_Y); break;
	        case 'Z': doType(command, VK_SHIFT, VK_Z); break;
	        case '`': doType(command, VK_BACK_QUOTE); break;
	        case '0': doType(command, VK_0); break;
	        case '1': doType(command, VK_1); break;
	        case '2': doType(command, VK_2); break;
	        case '3': doType(command, VK_3); break;
	        case '4': doType(command, VK_4); break;
	        case '5': doType(command, VK_5); break;
	        case '6': doType(command, VK_6); break;
	        case '7': doType(command, VK_7); break;
	        case '8': doType(command, VK_8); break;
	        case '9': doType(command, VK_9); break;
	        case '-': doType(command, VK_MINUS); break;
	        case '=': doType(command, VK_EQUALS); break;
	        case '~': doType(command, VK_SHIFT, VK_BACK_QUOTE); break;
	        case '!': doType(command, VK_SHIFT, VK_1); break;
	        case '@': doType(command, VK_AT); break;
	        case '#': doType(command, VK_NUMBER_SIGN); break;
	        case '$': doType(command, VK_DOLLAR); break;
	        case '%': doType(command, VK_SHIFT, VK_5); break;
	        case '^': doType(command, VK_CIRCUMFLEX); break;
	        case '&': doType(command, VK_AMPERSAND); break;
	        case '*': doType(command, VK_ASTERISK); break;
	        case '(': doType(command, VK_LEFT_PARENTHESIS); break;
	        case ')': doType(command, VK_RIGHT_PARENTHESIS); break;
	        case '_': doType(command, VK_UNDERSCORE); break;
	        case '+': doType(command, VK_PLUS); break;
	        case '\t': doType(command, VK_TAB); break;
	        case '\n': doType(command, VK_ENTER); break;
	        case '[': doType(command, VK_OPEN_BRACKET); break;
	        case ']': doType(command, VK_CLOSE_BRACKET); break;
	        case '\\': doType(command, VK_BACK_SLASH); break;
	        case '{': doType(command, VK_SHIFT, VK_OPEN_BRACKET); break;
	        case '}': doType(command, VK_SHIFT, VK_CLOSE_BRACKET); break;
	        case '|': doType(command, VK_SHIFT, VK_BACK_SLASH); break;
	        case ';': doType(command, VK_SEMICOLON); break;
	        case ':': doType(command, VK_COLON); break;
	        case '\'': doType(command, VK_QUOTE); break;
	        case '"': doType(command, VK_QUOTEDBL); break;
	        case ',': doType(command, VK_COMMA); break;
	        case '<': doType(command, VK_SHIFT, VK_COMMA); break;
	        case '.': doType(command, VK_PERIOD); break;
	        case '>': doType(command, VK_SHIFT, VK_PERIOD); break;
	        case '/': doType(command, VK_SLASH); break;
	        case '?': doType(command, VK_SHIFT, VK_SLASH); break;
	        case ' ': doType(command, VK_SPACE); break;
	        default:
	            throw new IllegalArgumentException("Cannot type character " + character);
	        }
    	} else {
    		switch(chars) {
			case "CTRL":
				doType(command, KeyEvent.VK_CONTROL);
				break;
			case "SHIFT":
				doType(command, KeyEvent.VK_SHIFT);
				break;
			case "ALT":
				doType(command, KeyEvent.VK_ALT);
				break;
			case "WIN":
				doType(command, KeyEvent.VK_WINDOWS);
				break;
			default:
				throw new IllegalArgumentException();
    		}
    	}
    }

    private void doType(int command, int... keyCodes) {
        doType(keyCodes, 0, keyCodes.length, command);
    }

    private void doType(int[] keyCodes, int offset, int length, int command) {
        if (length == 0) {
            return;
        }
        if(command == 0) {
	        robot.keyPress(keyCodes[offset]);
	        doType(keyCodes, offset + 1, length - 1, command);
	        robot.keyRelease(keyCodes[offset]);
        } else if(command == 1) {
        	robot.keyPress(keyCodes[offset]);
	        doType(keyCodes, offset + 1, length - 1, command);
        } else if(command == -1) {
        	robot.keyRelease(keyCodes[offset]);
	        doType(keyCodes, offset + 1, length - 1, command);
        }
    }

}