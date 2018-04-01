import java.util.*;
import java.io.*;

public class LexicalAnalyzer {
	State currentState = State.READY;
	String out = "";
	boolean endOfRawLex = false;
	Token outToken;

	public static void main(String[] args) {
		try {
			Scanner sc = new Scanner(new File("testcases/" + args[0]));
			PrintStream outText = new PrintStream(new File("outputs/" + args[0]));
			System.setOut(outText);

			LexicalAnalyzer la = new LexicalAnalyzer();

			while (sc.hasNext()) {
				la.getToken(sc.next());
			}

			if (la.currentState==State.COMMENT || la.currentState==State.COMMENT_ET_1 || la.currentState==State.COMMENT_ET_2) {
				System.out.println("***lexical error: un-expected end of file");
			}
		} catch (FileNotFoundException f) {
			System.out.println("ERROR: File not found");
		}
	}

	public void getToken(String rawLex) {
		int index = 0;
		char current;

		while (index<rawLex.length()) {
			current = rawLex.charAt(index);
			endOfRawLex = index==rawLex.length()-1;

			if (currentState==State.READY) {
				if (current=='+') {
					currentState = State.PLUS;
				} else if (current=='-') {
					currentState = State.MINUS;
				} else if (current=='*') {
					currentState = State.MULT;
				} else if (current=='/') {
					currentState = State.DIVIDE;
				} else if (current=='%') {
					currentState = State.MODULO;
				} else if (current=='(') {
					currentState = State.LPAREN;
				} else if (current==')') {
					currentState = State.RPAREN;
				} else if (current=='=') {
					currentState = State.EQUALS;
				} else if (current=='<') {
					currentState = State.LTHAN;
				} else if (current=='>') {
					currentState = State.GTHAN;
				} else if (current==':') {
					currentState = State.COLON;
				} else if (current==',') {
					currentState = State.COMMA;
				} else if (current==';') {
					currentState = State.SCOLON;
				} else if (current=='.') {
					currentState = State.PERIOD;
				} else if (current=='\'') {
					currentState = State.QUOTE;
				} else if (current=='\"') {
					currentState = State.DQUOTE;
				} else if (current>=48 && current<=57) {
					currentState = State.NUMBER;
				} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
					currentState = State.IDENT;
				} else {
					currentState = State.INVALID;
				}
			} else if (currentState==State.MULT) {
				if (current=='*') {
					currentState = State.EXP;
				} else {
					outToken = new Token("MULT      ", "*");
					displayToken(outToken);

					if (current=='+') {
						currentState = State.PLUS;
					} else if (current=='-') {
						currentState = State.MINUS;
					} else if (current=='*') {
						currentState = State.MULT;
					} else if (current=='/') {
						currentState = State.DIVIDE;
					} else if (current=='%') {
						currentState = State.MODULO;
					} else if (current=='(') {
						currentState = State.LPAREN;
					} else if (current==')') {
						currentState = State.RPAREN;
					} else if (current=='=') {
						currentState = State.EQUALS;
					} else if (current=='<') {
						currentState = State.LTHAN;
					} else if (current=='>') {
						currentState = State.GTHAN;
					} else if (current==':') {
						currentState = State.COLON;
					} else if (current==',') {
						currentState = State.COMMA;
					} else if (current==';') {
						currentState = State.SCOLON;
					} else if (current=='.') {
						currentState = State.PERIOD;
					} else if (current=='\'') {
						currentState = State.QUOTE;
					} else if (current=='\"') {
						currentState = State.DQUOTE;
					} else if (current>=48 && current<=57) {
						currentState = State.NUMBER;
					} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
						currentState = State.IDENT;
					} else {
						currentState = State.INVALID;
					}
				}
			} else if (currentState==State.LTHAN) {
				if (current=='/') {
					currentState = State.ENDTAGHEAD;
				} else if (current=='!') {
					currentState = State.COMMENT_HT_1;
				} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
					currentState = State.TAGIDENT;
				} else {
					outToken = new Token("LTHAN     ", "<");
					displayToken(outToken);

					if (current=='+') {
						currentState = State.PLUS;
					} else if (current=='-') {
						currentState = State.MINUS;
					} else if (current=='*') {
						currentState = State.MULT;
					} else if (current=='/') {
						currentState = State.DIVIDE;
					} else if (current=='%') {
						currentState = State.MODULO;
					} else if (current=='(') {
						currentState = State.LPAREN;
					} else if (current==')') {
						currentState = State.RPAREN;
					} else if (current=='=') {
						currentState = State.EQUALS;
					} else if (current=='<') {
						currentState = State.LTHAN;
					} else if (current=='>') {
						currentState = State.GTHAN;
					} else if (current==':') {
						currentState = State.COLON;
					} else if (current==',') {
						currentState = State.COMMA;
					} else if (current==';') {
						currentState = State.SCOLON;
					} else if (current=='.') {
						currentState = State.PERIOD;
					} else if (current=='\'') {
						currentState = State.QUOTE;
					} else if (current=='\"') {
						currentState = State.DQUOTE;
					} else if (current>=48 && current<=57) {
						currentState = State.NUMBER;
					} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
						currentState = State.IDENT;
					} else {
						currentState = State.INVALID;
					}
				}
			} else if (currentState==State.COMMENT_HT_1) {
				if (current=='-') {
					currentState = State.COMMENT_HT_2;
				} else {
					outToken = new Token("LTHAN     ", "<");
					displayToken(outToken);

					outToken = new Token("***lexical error:", "illegal character (!)");
					displayToken(outToken);

					if (current=='+') {
						currentState = State.PLUS;
					} else if (current=='-') {
						currentState = State.MINUS;
					} else if (current=='*') {
						currentState = State.MULT;
					} else if (current=='/') {
						currentState = State.DIVIDE;
					} else if (current=='%') {
						currentState = State.MODULO;
					} else if (current=='(') {
						currentState = State.LPAREN;
					} else if (current==')') {
						currentState = State.RPAREN;
					} else if (current=='=') {
						currentState = State.EQUALS;
					} else if (current=='<') {
						currentState = State.LTHAN;
					} else if (current=='>') {
						currentState = State.GTHAN;
					} else if (current==':') {
						currentState = State.COLON;
					} else if (current==',') {
						currentState = State.COMMA;
					} else if (current==';') {
						currentState = State.SCOLON;
					} else if (current=='.') {
						currentState = State.PERIOD;
					} else if (current=='\'') {
						currentState = State.QUOTE;
					} else if (current=='\"') {
						currentState = State.DQUOTE;
					} else if (current>=48 && current<=57) {
						currentState = State.NUMBER;
					} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
						currentState = State.IDENT;
					} else {
						currentState = State.INVALID;
					}
				}
			} else if (currentState==State.COMMENT_HT_2) {
				if (current=='-') {
					currentState = State.COMMENT;
				} else {
					outToken = new Token("LTHAN     ", "<");
					displayToken(outToken);

					outToken = new Token("***lexical error:", "illegal character (!)");
					displayToken(outToken);

					outToken = new Token("MINUS     ", "-");
					displayToken(outToken);

					if (current=='+') {
						currentState = State.PLUS;
					} else if (current=='-') {
						currentState = State.MINUS;
					} else if (current=='*') {
						currentState = State.MULT;
					} else if (current=='/') {
						currentState = State.DIVIDE;
					} else if (current=='%') {
						currentState = State.MODULO;
					} else if (current=='(') {
						currentState = State.LPAREN;
					} else if (current==')') {
						currentState = State.RPAREN;
					} else if (current=='=') {
						currentState = State.EQUALS;
					} else if (current=='<') {
						currentState = State.LTHAN;
					} else if (current=='>') {
						currentState = State.GTHAN;
					} else if (current==':') {
						currentState = State.COLON;
					} else if (current==',') {
						currentState = State.COMMA;
					} else if (current==';') {
						currentState = State.SCOLON;
					} else if (current=='.') {
						currentState = State.PERIOD;
					} else if (current=='\'') {
						currentState = State.QUOTE;
					} else if (current=='\"') {
						currentState = State.DQUOTE;
					} else if (current>=48 && current<=57) {
						currentState = State.NUMBER;
					} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
						currentState = State.IDENT;
					} else {
						currentState = State.INVALID;
					}
				}
			} else if (currentState==State.COMMENT) {
				if (current=='-') {
					currentState = State.COMMENT_ET_1;
				}
			} else if (currentState==State.COMMENT_ET_1) {
				if (current=='-') {
					currentState = State.COMMENT_ET_2;
				} else {
					currentState = State.COMMENT;
				}
			} else if (currentState==State.COMMENT_ET_2) {
				if (current=='>') {
					currentState = State.READY;
				} else {
					currentState = State.COMMENT;
				}
			} else if (currentState==State.TAGIDENT) {
				if (!((current>=65 && current<=90) || (current>=97 && current<=122))) {
					outToken = new Token("TAGIDENT  ", "<" + out);
					displayToken(outToken);
					out = "";

					if (current=='+') {
						currentState = State.PLUS;
					} else if (current=='-') {
						currentState = State.MINUS;
					} else if (current=='*') {
						currentState = State.MULT;
					} else if (current=='/') {
						currentState = State.DIVIDE;
					} else if (current=='%') {
						currentState = State.MODULO;
					} else if (current=='(') {
						currentState = State.LPAREN;
					} else if (current==')') {
						currentState = State.RPAREN;
					} else if (current=='=') {
						currentState = State.EQUALS;
					} else if (current=='<') {
						currentState = State.LTHAN;
					} else if (current=='>') {
						currentState = State.GTHAN;
					} else if (current==':') {
						currentState = State.COLON;
					} else if (current==',') {
						currentState = State.COMMA;
					} else if (current==';') {
						currentState = State.SCOLON;
					} else if (current=='.') {
						currentState = State.PERIOD;
					} else if (current=='\'') {
						currentState = State.QUOTE;
					} else if (current=='\"') {
						currentState = State.DQUOTE;
					} else if (current>=48 && current<=57) {
						currentState = State.NUMBER;
					} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
						currentState = State.IDENT;
					} else {
						currentState = State.INVALID;
					}
				}
			} else if (currentState==State.NUMBER) {
				if (current=='E' || current=='e') {
					currentState = State.FIRST_E;
				} else if (current=='.') {
					currentState = State.DECIMAL;
				} else if (!(current>=48 && current<=57)) {
					outToken = new Token("NUMBER    ", out);
					displayToken(outToken);
					out = "";

					if (current=='+') {
						currentState = State.PLUS;
					} else if (current=='-') {
						currentState = State.MINUS;
					} else if (current=='*') {
						currentState = State.MULT;
					} else if (current=='/') {
						currentState = State.DIVIDE;
					} else if (current=='%') {
						currentState = State.MODULO;
					} else if (current=='(') {
						currentState = State.LPAREN;
					} else if (current==')') {
						currentState = State.RPAREN;
					} else if (current=='=') {
						currentState = State.EQUALS;
					} else if (current=='<') {
						currentState = State.LTHAN;
					} else if (current=='>') {
						currentState = State.GTHAN;
					} else if (current==':') {
						currentState = State.COLON;
					} else if (current==',') {
						currentState = State.COMMA;
					} else if (current==';') {
						currentState = State.SCOLON;
					} else if (current=='.') {
						currentState = State.PERIOD;
					} else if (current=='\'') {
						currentState = State.QUOTE;
					} else if (current=='\"') {
						currentState = State.DQUOTE;
					} else if (current>=48 && current<=57) {
						currentState = State.NUMBER;
					} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
						currentState = State.IDENT;
					} else {
						currentState = State.INVALID;
					}
				}
			} else if (currentState==State.FIRST_E) {
				if (current=='+' || current=='-') {
					currentState = State.PLUS_MINUS;
				} else if (current>=48 && current<=57) {
					currentState = State.NUM_AFTER_E;
				} else {
					currentState = State.BAD_NUM;
				}
			} else if (currentState==State.PLUS_MINUS) {
				if (current>=48 && current<=57) {
					currentState = State.NUM_AFTER_E;
				} else {
					currentState = State.BAD_NUM;
				}
			} else if (currentState==State.NUM_AFTER_E) {
				if (!(current>=48 && current<=57)) {
					outToken = new Token("NUMBER    ", out);
					displayToken(outToken);
					out = "";
					currentState = State.READY;

					if (current=='+') {
						currentState = State.PLUS;
					} else if (current=='-') {
						currentState = State.MINUS;
					} else if (current=='*') {
						currentState = State.MULT;
					} else if (current=='/') {
						currentState = State.DIVIDE;
					} else if (current=='%') {
						currentState = State.MODULO;
					} else if (current=='(') {
						currentState = State.LPAREN;
					} else if (current==')') {
						currentState = State.RPAREN;
					} else if (current=='=') {
						currentState = State.EQUALS;
					} else if (current=='<') {
						currentState = State.LTHAN;
					} else if (current=='>') {
						currentState = State.GTHAN;
					} else if (current==':') {
						currentState = State.COLON;
					} else if (current==',') {
						currentState = State.COMMA;
					} else if (current==';') {
						currentState = State.SCOLON;
					} else if (current=='.') {
						currentState = State.PERIOD;
					} else if (current=='\'') {
						currentState = State.QUOTE;
					} else if (current=='\"') {
						currentState = State.DQUOTE;
					} else if (current>=48 && current<=57) {
						currentState = State.NUMBER;
					} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
						currentState = State.IDENT;
					} else {
						currentState = State.INVALID;
					}
				}
			} else if (currentState==State.DECIMAL) {
				if (current>=48 && current<=57) {
					currentState = State.DECIMAL_NUMBER;
				} else {
					currentState = State.BAD_NUM;
				}
			} else if (currentState==State.DECIMAL_NUMBER) {
				if (current=='E' || current=='e') {
					currentState = State.SECOND_E;
				} else if (!(current>=48 && current<=57)) {
					outToken = new Token("NUMBER    ", out);
					displayToken(outToken);
					out = "";

					if (current=='+') {
						currentState = State.PLUS;
					} else if (current=='-') {
						currentState = State.MINUS;
					} else if (current=='*') {
						currentState = State.MULT;
					} else if (current=='/') {
						currentState = State.DIVIDE;
					} else if (current=='%') {
						currentState = State.MODULO;
					} else if (current=='(') {
						currentState = State.LPAREN;
					} else if (current==')') {
						currentState = State.RPAREN;
					} else if (current=='=') {
						currentState = State.EQUALS;
					} else if (current=='<') {
						currentState = State.LTHAN;
					} else if (current=='>') {
						currentState = State.GTHAN;
					} else if (current==':') {
						currentState = State.COLON;
					} else if (current==',') {
						currentState = State.COMMA;
					} else if (current==';') {
						currentState = State.SCOLON;
					} else if (current=='.') {
						currentState = State.PERIOD;
					} else if (current=='\'') {
						currentState = State.QUOTE;
					} else if (current=='\"') {
						currentState = State.DQUOTE;
					} else if (current>=48 && current<=57) {
						currentState = State.NUMBER;
					} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
						currentState = State.IDENT;
					} else {
						currentState = State.INVALID;
					}
				}
			} else if (currentState==State.SECOND_E) {
				if (current=='+' || current=='-') {
					currentState = State.PLUS_MINUS;
				} else if (current>=48 && current<=57) {
					currentState = State.NUM_AFTER_E;
				} else {
					currentState = State.BAD_NUM;
				}
			} else if (currentState==State.IDENT) {
				if (!((current>=65 && current<=90) || (current>=97 && current<=122))) {
					outToken = new Token("IDENT     ", out);
					displayToken(outToken);
					out = "";

					if (current=='+') {
						currentState = State.PLUS;
					} else if (current=='-') {
						currentState = State.MINUS;
					} else if (current=='*') {
						currentState = State.MULT;
					} else if (current=='/') {
						currentState = State.DIVIDE;
					} else if (current=='%') {
						currentState = State.MODULO;
					} else if (current=='(') {
						currentState = State.LPAREN;
					} else if (current==')') {
						currentState = State.RPAREN;
					} else if (current=='=') {
						currentState = State.EQUALS;
					} else if (current=='<') {
						currentState = State.LTHAN;
					} else if (current=='>') {
						currentState = State.GTHAN;
					} else if (current==':') {
						currentState = State.COLON;
					} else if (current==',') {
						currentState = State.COMMA;
					} else if (current==';') {
						currentState = State.SCOLON;
					} else if (current=='.') {
						currentState = State.PERIOD;
					} else if (current=='\'') {
						currentState = State.QUOTE;
					} else if (current=='\"') {
						currentState = State.DQUOTE;
					} else if (current>=48 && current<=57) {
						currentState = State.NUMBER;
					} else if ((current>=65 && current<=90) || (current>=97 && current<=122)) {
						currentState = State.IDENT;
					} else {
						currentState = State.INVALID;
					}
				}
			}

			switch (currentState) {
				case PLUS:
					outToken = new Token("PLUS      ", "+");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case MINUS:
					outToken = new Token("MINUS     ", "-");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case MULT:
					if (endOfRawLex) {
						outToken = new Token("MULT      ", "*");
						displayToken(outToken);
						currentState = State.READY;
					} 
					break;

				case EXP:
					outToken = new Token("EXP       ", "**");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case DIVIDE:
					outToken = new Token("DIVIDE    ", "/");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case MODULO:
					outToken = new Token("MODULO    ", "%");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case LPAREN:
					outToken = new Token("LPAREN    ", "(");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case RPAREN:
					outToken = new Token("RPAREN    ", ")");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case EQUALS:
					outToken = new Token("EQUALS    ", "=");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case LTHAN:
					if (endOfRawLex) {
						outToken = new Token("LTHAN     ", "<");
						displayToken(outToken);
						currentState = State.READY;
					}
					break;

				case ENDTAGHEAD:
					outToken = new Token("ENDTAGHEAD", "</");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case COMMENT_HT_1:
					if (endOfRawLex) {
						outToken = new Token("LTHAN     ", "<");
						displayToken(outToken);

						outToken = new Token("***lexical error:", "illegal character (!)");
						displayToken(outToken);
						currentState = State.READY;
					}
					break;

				case COMMENT_HT_2:
					if (endOfRawLex) {
						outToken = new Token("LTHAN     ", "<");
						displayToken(outToken);

						outToken = new Token("***lexical error:", "illegal character (!)");
						displayToken(outToken);

						outToken = new Token("MINUS     ", "-");
						displayToken(outToken);
						currentState = State.READY;
					}
					break;

				case TAGIDENT:
					out += current;

					if (endOfRawLex) {
						outToken = new Token("TAGIDENT  ", "<" + out);
						displayToken(outToken);
						out = "";
						currentState = State.READY;
					}
					break;

				case GTHAN:
					outToken = new Token("GTHAN     ", ">");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case COLON:
					outToken = new Token("COLON     ", ":");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case COMMA:
					outToken = new Token("COMMA     ", ",");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case SCOLON:
					outToken = new Token("SCOLON    ", ";");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case PERIOD:
					outToken = new Token("PERIOD    ", ".");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case QUOTE:
					outToken = new Token("QUOTE     ", "'");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case DQUOTE:
					outToken = new Token("DQUOTE    ", "\"");
					displayToken(outToken);
					currentState = State.READY;
					break;

				case NUMBER:
					out += current;

					if (endOfRawLex) {
						outToken = new Token("NUMBER    ", out);
						displayToken(outToken);
						out = "";
						currentState = State.READY;
					}
					break;

				case FIRST_E:
					out += current;

					if (endOfRawLex) {
						System.out.println("***lexical error: badly formed number");
						outToken = new Token("NUMBER    ", out);
						displayToken(outToken);
						out = "";
						currentState = State.READY;
					}
					break;

				case PLUS_MINUS:
					out += current;

					if (endOfRawLex) {
						System.out.println("***lexical error: badly formed number");
						outToken = new Token("NUMBER    ", out);
						displayToken(outToken);
						out = "";
						currentState = State.READY;
					}
					break;

				case NUM_AFTER_E:
					out += current;

					if (endOfRawLex) {
						outToken = new Token("NUMBER    ", out);
						displayToken(outToken);
						out = "";
						currentState = State.READY;
					}
					break;

				case BAD_NUM:
					out += current;
					System.out.println("***lexical error: badly formed number");
					outToken = new Token("NUMBER    ", out);
					displayToken(outToken);
					out = "";
					currentState = State.READY;
					break;

				case DECIMAL:
					out += current;

					if (endOfRawLex) {
						System.out.println("***lexical error: badly formed number");
						outToken = new Token("NUMBER    ", out);
						displayToken(outToken);
						out = "";
						currentState = State.READY;
					}
					break;

				case DECIMAL_NUMBER:
					out += current;

					if (endOfRawLex) {
						outToken = new Token("NUMBER    ", out);
						displayToken(outToken);
						out = "";
						currentState = State.READY;
					}
					break;

				case SECOND_E:
					out += current;

					if (endOfRawLex) {
						System.out.println("***lexical error: badly formed number");
						outToken = new Token("NUMBER    ", out);
						displayToken(outToken);
						out = "";
						currentState = State.READY;
					}
				break;

				case IDENT:
					out += current;

					if (endOfRawLex) {
						outToken = new Token("IDENT     ", out);
						displayToken(outToken);
						out = "";
						currentState = State.READY;
					}
					break;

				case INVALID:
					System.out.println("***lexical error: illegal character (" + current + ")");
					currentState = State.READY;
					break;
			}

			index++;
		}
	}

	public void displayToken(Token t) {
		System.out.println(t.getId() + " " + t.getLexeme());

	}

	public enum State {
		READY,
		PLUS,
		MINUS,
		MULT,
		EXP,
		DIVIDE,
		MODULO,
		LPAREN,
		RPAREN,
		EQUALS,
		LTHAN,
		ENDTAGHEAD,
		COMMENT_HT_1,
		COMMENT_HT_2,
		COMMENT,
		COMMENT_ET_1,
		COMMENT_ET_2,
		TAGIDENT,
		GTHAN,
		COLON,
		COMMA,
		SCOLON,
		PERIOD,
		QUOTE,
		DQUOTE,
		NUMBER,
		FIRST_E,
		DECIMAL,
		BAD_NUM,
		PLUS_MINUS,
		NUM_AFTER_E,
		DECIMAL_NUMBER,
		SECOND_E,
		IDENT,
		INVALID
	}
}