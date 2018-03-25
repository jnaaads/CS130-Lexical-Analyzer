import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.FileOutputStream;

public class LexicalAnalyzer {
	State s = State.READY;
	Token eofToken;
	Token eofBadNum;
	boolean endOfRawLex = false;
	String ident = "";
	String num = "";

	public static void main(String[] args) {
		try {

			Scanner sc = new Scanner((new File("testcases/" + args[0])));

			LexicalAnalyzer la = new LexicalAnalyzer();
			
			while (sc.hasNext()) {
				la.getToken(sc.next());
			}

			if (la.s==State.MULT || la.s==State.LTHAN || la.s==State.COM_HT1 || la.s==State.COM_HT2 || la.s==State.COMMENT 
				|| la.s==State.COM_ET1 || la.s==State.COM_ET2 || la.s==State.TAGIDENT || 
				la.s==State.NUMBER_1_NOE || la.s==State.DECIMAL_NOE || la.s==State.NUMBER_2_NOE || 
				la.s==State.NUMBER_1_WITHE || la.s==State.NUMBER_2_WITHE || la.s==State.NUMBER_WITHE_PLUSMINUS ||
				la.s==State.NUMBER_WITHE_END || la.s==State.IDENT) {
				la.displayToken(la.eofToken);
			}

			if (la.s==State.DECIMAL_NOE || la.s==State.NUMBER_WITHE_PLUSMINUS) {
				la.displayToken(la.eofBadNum);
			}

		} catch (FileNotFoundException e) {
			System.out.println("ERROR: File not found.");
		}
	}

	public void getToken(String rawLex) {
		int index = 0;
		Token t;

		while (index < rawLex.length()) {

			if (s==State.READY) {
				if (rawLex.charAt(index)=='+') {
					s = State.PLUS;
				} else if (rawLex.charAt(index)=='-') {
					s = State.MINUS;
				} else if (rawLex.charAt(index)=='*') {
					s = State.MULT;
				} else if (rawLex.charAt(index)=='/') {
					s = State.DIVIDE;
				} else if (rawLex.charAt(index)=='(') {
					s = State.LPAREN;
				} else if (rawLex.charAt(index)==')') {
					s = State.RPAREN;
				} else if (rawLex.charAt(index)=='=') {
					s = State.EQUALS;
				} else if (rawLex.charAt(index)=='<') {
					s = State.LTHAN;
				} else if (rawLex.charAt(index)=='>') {
					s = State.GTHAN;
				} else if (rawLex.charAt(index)==':') {
					s = State.COLON;
				} else if (rawLex.charAt(index)==';') {
					s = State.SCOLON;
				} else if (rawLex.charAt(index)=='.') {
					s = State.PERIOD;
				} else if (rawLex.charAt(index)==39) {
					s = State.QUOTE;
				} else if (rawLex.charAt(index)==34) {
					s = State.DQUOTE;
				} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
					s = State.NUMBER_1_NOE;
				} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
					s = State.IDENT;
				}

			} else if (s==State.MULT) {
				if (rawLex.charAt(index)=='*' && !endOfRawLex) {
					s = State.EXP;
				} else {
					t = new Token();
					t.setId(s.toString() + "      ");
					t.setLexeme("*");
					displayToken(t);
					endOfRawLex = false;

					if (rawLex.charAt(index)=='+') {
						s = State.PLUS;
					} else if (rawLex.charAt(index)=='-') {
						s = State.MINUS;
					} else if (rawLex.charAt(index)=='*') {
						s = State.MULT;
					} else if (rawLex.charAt(index)=='/') {
						s = State.DIVIDE;
					} else if (rawLex.charAt(index)=='(') {
						s = State.LPAREN;
					} else if (rawLex.charAt(index)==')') {
						s = State.RPAREN;
					} else if (rawLex.charAt(index)=='=') {
						s = State.EQUALS;
					} else if (rawLex.charAt(index)=='<') {
						s = State.LTHAN;
					} else if (rawLex.charAt(index)=='>') {
						s = State.GTHAN;
					} else if (rawLex.charAt(index)==':') {
						s = State.COLON;
					} else if (rawLex.charAt(index)==';') {
						s = State.SCOLON;
					} else if (rawLex.charAt(index)=='.') {
						s = State.PERIOD;
					} else if (rawLex.charAt(index)==39) {
						s = State.QUOTE;
					} else if (rawLex.charAt(index)==34) {
						s = State.DQUOTE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
						s = State.IDENT;
					}
				}

			} else if (s==State.LTHAN) {
				if (rawLex.charAt(index)=='!' && !endOfRawLex) {
					s = State.COM_HT1;
				} else if (rawLex.charAt(index)=='/' && !endOfRawLex) {
					s = State.ENDTAGHEAD;
				} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122) && !endOfRawLex) {
					s = State.TAGIDENT;
				} else {
					t = new Token();
					t.setId(s.toString() + "     ");
					t.setLexeme("<");
					displayToken(t);
					endOfRawLex = false;

					if (rawLex.charAt(index)=='+') {
						s = State.PLUS;
					} else if (rawLex.charAt(index)=='-') {
						s = State.MINUS;
					} else if (rawLex.charAt(index)=='*') {
						s = State.MULT;
					} else if (rawLex.charAt(index)=='/') {
						s = State.DIVIDE;
					} else if (rawLex.charAt(index)=='(') {
						s = State.LPAREN;
					} else if (rawLex.charAt(index)==')') {
						s = State.RPAREN;
					} else if (rawLex.charAt(index)=='=') {
						s = State.EQUALS;
					} else if (rawLex.charAt(index)=='<') {
						s = State.LTHAN;
					} else if (rawLex.charAt(index)=='>') {
						s = State.GTHAN;
					} else if (rawLex.charAt(index)==':') {
						s = State.COLON;
					} else if (rawLex.charAt(index)==';') {
						s = State.SCOLON;
					} else if (rawLex.charAt(index)=='.') {
						s = State.PERIOD;
					} else if (rawLex.charAt(index)==39) {
						s = State.QUOTE;
					} else if (rawLex.charAt(index)==34) {
						s = State.DQUOTE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
						s = State.IDENT;
					}
				}

			} else if (s==State.COM_HT1) {
				if (rawLex.charAt(index)=='-' && !endOfRawLex) {
					s = State.COM_HT2;
				} else {
					t = new Token();
					s = State.LTHAN;
					t.setId(s.toString() + "     ");
					t.setLexeme("<");
					displayToken(t);

					t.setId("***lexical error: ");
					t.setLexeme("illegal expression (!)");
					displayToken(t);
					endOfRawLex = false;

					if (rawLex.charAt(index)=='+') {
						s = State.PLUS;
					} else if (rawLex.charAt(index)=='-') {
						s = State.MINUS;
					} else if (rawLex.charAt(index)=='*') {
						s = State.MULT;
					} else if (rawLex.charAt(index)=='/') {
						s = State.DIVIDE;
					} else if (rawLex.charAt(index)=='(') {
						s = State.LPAREN;
					} else if (rawLex.charAt(index)==')') {
						s = State.RPAREN;
					} else if (rawLex.charAt(index)=='=') {
						s = State.EQUALS;
					} else if (rawLex.charAt(index)=='<') {
						s = State.LTHAN;
					} else if (rawLex.charAt(index)=='>') {
						s = State.GTHAN;
					} else if (rawLex.charAt(index)==':') {
						s = State.COLON;
					} else if (rawLex.charAt(index)==';') {
						s = State.SCOLON;
					} else if (rawLex.charAt(index)=='.') {
						s = State.PERIOD;
					} else if (rawLex.charAt(index)==39) {
						s = State.QUOTE;
					} else if (rawLex.charAt(index)==34) {
						s = State.DQUOTE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
						s = State.IDENT;
					}
				}

			} else if (s==State.COM_HT2) {
				if (rawLex.charAt(index)=='-'&& !endOfRawLex) {
					s = State.COMMENT;
				} else {
					t = new Token();
					s = State.LTHAN;
					t.setId(s.toString() + "     ");
					t.setLexeme("<");
					displayToken(t);

					t.setId("***lexical error: ");
					t.setLexeme("illegal expression (!)");
					displayToken(t);

					s = State.MINUS;
					t.setId(s.toString() + "     ");
					t.setLexeme("-");
					displayToken(t);
					endOfRawLex = false;

					if (rawLex.charAt(index)=='+') {
						s = State.PLUS;
					} else if (rawLex.charAt(index)=='-') {
						s = State.MINUS;
					} else if (rawLex.charAt(index)=='*') {
						s = State.MULT;
					} else if (rawLex.charAt(index)=='/') {
						s = State.DIVIDE;
					} else if (rawLex.charAt(index)=='(') {
						s = State.LPAREN;
					} else if (rawLex.charAt(index)==')') {
						s = State.RPAREN;
					} else if (rawLex.charAt(index)=='=') {
						s = State.EQUALS;
					} else if (rawLex.charAt(index)=='<') {
						s = State.LTHAN;
					} else if (rawLex.charAt(index)=='>') {
						s = State.GTHAN;
					} else if (rawLex.charAt(index)==':') {
						s = State.COLON;
					} else if (rawLex.charAt(index)==';') {
						s = State.SCOLON;
					} else if (rawLex.charAt(index)=='.') {
						s = State.PERIOD;
					} else if (rawLex.charAt(index)==39) {
						s = State.QUOTE;
					} else if (rawLex.charAt(index)==34) {
						s = State.DQUOTE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
						s = State.IDENT;
					}
				}

			} else if (s==State.COMMENT) {
				if (rawLex.charAt(index)=='-') {
					s = State.COM_ET1;
				} else {
					s = State.COMMENT;
				}

			} else if (s==State.COM_ET1) {
				if (rawLex.charAt(index)=='-' && !endOfRawLex) {
					s = State.COM_ET2;
				} else {
					s = State.COMMENT;
					endOfRawLex = false;
				}

			} else if (s==State.COM_ET2) {
				if (rawLex.charAt(index)=='>'&& !endOfRawLex) {
					s = State.READY;
				} else {
					s = State.COMMENT;
					endOfRawLex = false;
				}	

			} else if (s==State.TAGIDENT) {
				if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122) && !endOfRawLex) {
					s = State.TAGIDENT;
				} else {
					t = new Token();
					t.setId(s.toString() + "  ");
					t.setLexeme("<" + ident);
					displayToken(t);
					s = State.READY;
					ident = "";
					endOfRawLex = false;

					if (rawLex.charAt(index)=='+') {
						s = State.PLUS;
					} else if (rawLex.charAt(index)=='-') {
						s = State.MINUS;
					} else if (rawLex.charAt(index)=='*') {
						s = State.MULT;
					} else if (rawLex.charAt(index)=='/') {
						s = State.DIVIDE;
					} else if (rawLex.charAt(index)=='(') {
						s = State.LPAREN;
					} else if (rawLex.charAt(index)==')') {
						s = State.RPAREN;
					} else if (rawLex.charAt(index)=='=') {
						s = State.EQUALS;
					} else if (rawLex.charAt(index)=='<') {
						s = State.LTHAN;
					} else if (rawLex.charAt(index)=='>') {
						s = State.GTHAN;
					} else if (rawLex.charAt(index)==':') {
						s = State.COLON;
					} else if (rawLex.charAt(index)==';') {
						s = State.SCOLON;
					} else if (rawLex.charAt(index)=='.') {
						s = State.PERIOD;
					} else if (rawLex.charAt(index)==39) {
						s = State.QUOTE;
					} else if (rawLex.charAt(index)==34) {
						s = State.DQUOTE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
						s = State.IDENT;
					}
				}

			} else if (s==State.NUMBER_1_NOE) {
				if ((rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) && !endOfRawLex) {
					s = State.NUMBER_1_NOE;
				} else if (rawLex.charAt(index)=='.' && !endOfRawLex) {
					s = State.DECIMAL_NOE;
				} else if ((rawLex.charAt(index)=='e' || rawLex.charAt(index)=='E') && !endOfRawLex) {
					s = State.NUMBER_1_WITHE;
				} else {
					t = new Token();
					t.setId("NUMBER    ");
					t.setLexeme(num);
					displayToken(t);
					num = "";
					endOfRawLex = false;

					if (rawLex.charAt(index)=='+') {
						s = State.PLUS;
					} else if (rawLex.charAt(index)=='-') {
						s = State.MINUS;
					} else if (rawLex.charAt(index)=='*') {
						s = State.MULT;
					} else if (rawLex.charAt(index)=='/') {
						s = State.DIVIDE;
					} else if (rawLex.charAt(index)=='(') {
						s = State.LPAREN;
					} else if (rawLex.charAt(index)==')') {
						s = State.RPAREN;
					} else if (rawLex.charAt(index)=='=') {
						s = State.EQUALS;
					} else if (rawLex.charAt(index)=='<') {
						s = State.LTHAN;
					} else if (rawLex.charAt(index)=='>') {
						s = State.GTHAN;
					} else if (rawLex.charAt(index)==':') {
						s = State.COLON;
					} else if (rawLex.charAt(index)==';') {
						s = State.SCOLON;
					} else if (rawLex.charAt(index)==39) {
						s = State.QUOTE;
					} else if (rawLex.charAt(index)==34) {
						s = State.DQUOTE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
						s = State.IDENT;
					}
				}		

			} else if (s==State.DECIMAL_NOE) {
				if ((rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) && !endOfRawLex) {
					s = State.NUMBER_2_NOE;
				} else {
					if (endOfRawLex) {
						t = new Token();
						t.setId("***lexical error:");
						t.setLexeme("badly formed number");
						displayToken(t);
						t.setId("NUMBER    ");
						t.setLexeme(num);
						displayToken(t);
						num = "";
						endOfRawLex = false;

						if (rawLex.charAt(index)=='+') {
							s = State.PLUS;
						} else if (rawLex.charAt(index)=='-') {
							s = State.MINUS;
						} else if (rawLex.charAt(index)=='*') {
							s = State.MULT;
						} else if (rawLex.charAt(index)=='/') {
							s = State.DIVIDE;
						} else if (rawLex.charAt(index)=='(') {
							s = State.LPAREN;
						} else if (rawLex.charAt(index)==')') {
							s = State.RPAREN;
						} else if (rawLex.charAt(index)=='=') {
							s = State.EQUALS;
						} else if (rawLex.charAt(index)=='<') {
							s = State.LTHAN;
						} else if (rawLex.charAt(index)=='>') {
							s = State.GTHAN;
						} else if (rawLex.charAt(index)==':') {
							s = State.COLON;
						} else if (rawLex.charAt(index)=='.') {
							s = State.PERIOD;
						} else if (rawLex.charAt(index)==';') {
							s = State.SCOLON;
						} else if (rawLex.charAt(index)==39) {
							s = State.QUOTE;
						} else if (rawLex.charAt(index)==34) {
							s = State.DQUOTE;
						} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
							s = State.NUMBER_1_NOE;
						} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
							s = State.IDENT;
						}
					} else {
						s = State.BAD_NUMBER;
					}
				}

			} else if (s==State.NUMBER_2_NOE) {
				if ((rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) && !endOfRawLex) {
					s = State.NUMBER_2_NOE;
				} else if ((rawLex.charAt(index)=='e' || rawLex.charAt(index)=='E') && !endOfRawLex) {
					s = State.NUMBER_2_WITHE;
				} else {
					t = new Token();
					t.setId("NUMBER    ");
					t.setLexeme(num);
					displayToken(t);
					num = "";
					endOfRawLex = false;

					if (rawLex.charAt(index)=='+') {
						s = State.PLUS;
					} else if (rawLex.charAt(index)=='-') {
						s = State.MINUS;
					} else if (rawLex.charAt(index)=='*') {
						s = State.MULT;
					} else if (rawLex.charAt(index)=='/') {
						s = State.DIVIDE;
					} else if (rawLex.charAt(index)=='(') {
						s = State.LPAREN;
					} else if (rawLex.charAt(index)==')') {
						s = State.RPAREN;
					} else if (rawLex.charAt(index)=='=') {
						s = State.EQUALS;
					} else if (rawLex.charAt(index)=='<') {
						s = State.LTHAN;
					} else if (rawLex.charAt(index)=='>') {
						s = State.GTHAN;
					} else if (rawLex.charAt(index)==':') {
						s = State.COLON;
					} else if (rawLex.charAt(index)=='.') {
						s = State.PERIOD;
					} else if (rawLex.charAt(index)==';') {
						s = State.SCOLON;
					} else if (rawLex.charAt(index)==39) {
						s = State.QUOTE;
					} else if (rawLex.charAt(index)==34) {
						s = State.DQUOTE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
						s = State.IDENT;
					}
				}

			} else if (s==State.NUMBER_1_WITHE) {
				if ((rawLex.charAt(index)=='+' && rawLex.charAt(index)=='-') && !endOfRawLex) {
					s = State.NUMBER_WITHE_PLUSMINUS;
				} else if ((rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) && !endOfRawLex) {
					s = State.NUMBER_WITHE_END;
				} else {
					if (endOfRawLex) {
						t = new Token();
						t.setId("***lexical error:");
						t.setLexeme("badly formed number");
						displayToken(t);
						t.setId("NUMBER    ");
						t.setLexeme(num);
						displayToken(t);
						num = "";
						endOfRawLex = false;

						if (rawLex.charAt(index)=='+') {
							s = State.PLUS;
						} else if (rawLex.charAt(index)=='-') {
							s = State.MINUS;
						} else if (rawLex.charAt(index)=='*') {
							s = State.MULT;
						} else if (rawLex.charAt(index)=='/') {
							s = State.DIVIDE;
						} else if (rawLex.charAt(index)=='(') {
							s = State.LPAREN;
						} else if (rawLex.charAt(index)==')') {
							s = State.RPAREN;
						} else if (rawLex.charAt(index)=='=') {
							s = State.EQUALS;
						} else if (rawLex.charAt(index)=='<') {
							s = State.LTHAN;
						} else if (rawLex.charAt(index)=='>') {
							s = State.GTHAN;
						} else if (rawLex.charAt(index)==':') {
							s = State.COLON;
						} else if (rawLex.charAt(index)=='.') {
							s = State.PERIOD;
						} else if (rawLex.charAt(index)==';') {
							s = State.SCOLON;
						} else if (rawLex.charAt(index)==39) {
							s = State.QUOTE;
						} else if (rawLex.charAt(index)==34) {
							s = State.DQUOTE;
						} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
							s = State.NUMBER_1_NOE;
						} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
							s = State.IDENT;
						}
					} else {
						s = State.BAD_NUMBER;
					}
				}

			} else if (s==State.NUMBER_2_WITHE) {
				if ((rawLex.charAt(index)=='+' && rawLex.charAt(index)=='-') && !endOfRawLex) {
					s = State.NUMBER_WITHE_PLUSMINUS;
				} else if ((rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) && !endOfRawLex) {
					s = State.NUMBER_WITHE_END;
				} else {
					if (endOfRawLex) {
						t = new Token();
						t.setId("***lexical error:");
						t.setLexeme("badly formed number");
						displayToken(t);
						t.setId("NUMBER    ");
						t.setLexeme(num);
						displayToken(t);
						num = "";
						endOfRawLex = false;

						if (rawLex.charAt(index)=='+') {
							s = State.PLUS;
						} else if (rawLex.charAt(index)=='-') {
							s = State.MINUS;
						} else if (rawLex.charAt(index)=='*') {
							s = State.MULT;
						} else if (rawLex.charAt(index)=='/') {
							s = State.DIVIDE;
						} else if (rawLex.charAt(index)=='(') {
							s = State.LPAREN;
						} else if (rawLex.charAt(index)==')') {
							s = State.RPAREN;
						} else if (rawLex.charAt(index)=='=') {
							s = State.EQUALS;
						} else if (rawLex.charAt(index)=='<') {
							s = State.LTHAN;
						} else if (rawLex.charAt(index)=='>') {
							s = State.GTHAN;
						} else if (rawLex.charAt(index)==':') {
							s = State.COLON;
						} else if (rawLex.charAt(index)=='.') {
							s = State.PERIOD;
						} else if (rawLex.charAt(index)==';') {
							s = State.SCOLON;
						} else if (rawLex.charAt(index)==39) {
							s = State.QUOTE;
						} else if (rawLex.charAt(index)==34) {
							s = State.DQUOTE;
						} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
							s = State.NUMBER_1_NOE;
						} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
							s = State.IDENT;
						}
					} else {
						s = State.BAD_NUMBER;
					}
				}

			} else if (s==State.NUMBER_WITHE_END) {
				if ((rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) && !endOfRawLex) {
					s = State.NUMBER_WITHE_END;
				} else {
					t = new Token();
					t.setId("NUMBER    ");
					t.setLexeme(num);
					displayToken(t);
					num = "";
					endOfRawLex = false;

					if (rawLex.charAt(index)=='+') {
						s = State.PLUS;
					} else if (rawLex.charAt(index)=='-') {
						s = State.MINUS;
					} else if (rawLex.charAt(index)=='*') {
						s = State.MULT;
					} else if (rawLex.charAt(index)=='/') {
						s = State.DIVIDE;
					} else if (rawLex.charAt(index)=='(') {
						s = State.LPAREN;
					} else if (rawLex.charAt(index)==')') {
						s = State.RPAREN;
					} else if (rawLex.charAt(index)=='=') {
						s = State.EQUALS;
					} else if (rawLex.charAt(index)=='<') {
						s = State.LTHAN;
					} else if (rawLex.charAt(index)=='>') {
						s = State.GTHAN;
					} else if (rawLex.charAt(index)==':') {
						s = State.COLON;
					} else if (rawLex.charAt(index)=='.') {
						s = State.PERIOD;
					} else if (rawLex.charAt(index)==';') {
						s = State.SCOLON;
					} else if (rawLex.charAt(index)==39) {
						s = State.QUOTE;
					} else if (rawLex.charAt(index)==34) {
						s = State.DQUOTE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
						s = State.IDENT;
					}
				}
				
			} else if (s==State.NUMBER_WITHE_PLUSMINUS) {
				if ((rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) && !endOfRawLex) {
					s = State.NUMBER_WITHE_END;
				} else {
					if (endOfRawLex) {
						t = new Token();
						t.setId("***lexical error:");
						t.setLexeme("badly formed number");
						displayToken(t);
						t.setId("NUMBER    ");
						t.setLexeme(num);
						displayToken(t);
						num = "";
						endOfRawLex = false;

						if (rawLex.charAt(index)=='+') {
							s = State.PLUS;
						} else if (rawLex.charAt(index)=='-') {
							s = State.MINUS;
						} else if (rawLex.charAt(index)=='*') {
							s = State.MULT;
						} else if (rawLex.charAt(index)=='/') {
							s = State.DIVIDE;
						} else if (rawLex.charAt(index)=='(') {
							s = State.LPAREN;
						} else if (rawLex.charAt(index)==')') {
							s = State.RPAREN;
						} else if (rawLex.charAt(index)=='=') {
							s = State.EQUALS;
						} else if (rawLex.charAt(index)=='<') {
							s = State.LTHAN;
						} else if (rawLex.charAt(index)=='>') {
							s = State.GTHAN;
						} else if (rawLex.charAt(index)==':') {
							s = State.COLON;
						} else if (rawLex.charAt(index)=='.') {
							s = State.PERIOD;
						} else if (rawLex.charAt(index)==';') {
							s = State.SCOLON;
						} else if (rawLex.charAt(index)==39) {
							s = State.QUOTE;
						} else if (rawLex.charAt(index)==34) {
							s = State.DQUOTE;
						} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
							s = State.NUMBER_1_NOE;
						} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
							s = State.IDENT;
						}
					} else {
						s = State.BAD_NUMBER;
					}
				}
			} else if (s==State.IDENT) {
				if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122) && !endOfRawLex) {
					s = State.IDENT;
				} else {
					t = new Token();
					t.setId(s.toString() + "     ");
					t.setLexeme(ident);
					displayToken(t);
					ident = "";
					endOfRawLex = false;

					if (rawLex.charAt(index)=='+') {
						s = State.PLUS;
					} else if (rawLex.charAt(index)=='-') {
						s = State.MINUS;
					} else if (rawLex.charAt(index)=='*') {
						s = State.MULT;
					} else if (rawLex.charAt(index)=='/') {
						s = State.DIVIDE;
					} else if (rawLex.charAt(index)=='(') {
						s = State.LPAREN;
					} else if (rawLex.charAt(index)==')') {
						s = State.RPAREN;
					} else if (rawLex.charAt(index)=='=') {
						s = State.EQUALS;
					} else if (rawLex.charAt(index)=='<') {
						s = State.LTHAN;
					} else if (rawLex.charAt(index)=='>') {
						s = State.GTHAN;
					} else if (rawLex.charAt(index)==':') {
						s = State.COLON;
					} else if (rawLex.charAt(index)==';') {
						s = State.SCOLON;
					} else if (rawLex.charAt(index)=='.') {
						s = State.PERIOD;
					} else if (rawLex.charAt(index)==39) {
						s = State.QUOTE;
					} else if (rawLex.charAt(index)==34) {
						s = State.DQUOTE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if (rawLex.charAt(index)>=48 && rawLex.charAt(index)<=57) {
						s = State.NUMBER_1_NOE;
					} else if ((rawLex.charAt(index)>=65 && rawLex.charAt(index)<=90) || (rawLex.charAt(index)>=97 && rawLex.charAt(index)<=122)) {
							s = State.IDENT;
					}
				}
			}
		
			switch (s) {
				case PLUS:
					t = new Token();
					t.setId(s.toString() + "      ");
					t.setLexeme("+");
					displayToken(t);
					s = State.READY;
					break;

				case MINUS:
					t = new Token();
					t.setId(s.toString() + "     ");
					t.setLexeme("-");
					displayToken(t);
					s = State.READY;
					break;

				case MULT:
					eofToken = new Token();
					eofToken.setId(s.toString() + "      ");
					eofToken.setLexeme("*");

					endOfRawLex = index==rawLex.length()-1;
					break;

				case DIVIDE:
					t = new Token();
					t.setId(s.toString() + "    ");
					t.setLexeme("/");
					displayToken(t);
					s = State.READY;
					break;

				case EXP:
					t = new Token();
					t.setId(s.toString() + "       ");
					t.setLexeme("**");
					displayToken(t);
					s = State.READY;
					break;

				case LPAREN:
					t = new Token();
					t.setId(s.toString() + "    ");
					t.setLexeme("(");
					displayToken(t);
					s = State.READY;
					break;

				case RPAREN:
					t = new Token();
					t.setId(s.toString() + "    ");
					t.setLexeme(")");
					displayToken(t);
					s = State.READY;
					break;				

				case EQUALS:
					t = new Token();
					t.setId(s.toString() + "    ");
					t.setLexeme(")");
					displayToken(t);
					s = State.READY;
					break;

				case LTHAN:
					eofToken = new Token();
					eofToken.setId(s.toString() + "     ");
					eofToken.setLexeme("<");

					endOfRawLex = index==rawLex.length()-1;
					break;

				case COM_HT1:
					eofToken = new Token();
					eofToken.setId("***lexical error: ");
					eofToken.setLexeme("un-expected end of file");

					endOfRawLex = index==rawLex.length()-1;
					break;

				case COM_HT2:
					eofToken = new Token();
					eofToken.setId("***lexical error: ");
					eofToken.setLexeme("un-expected end of file");

					endOfRawLex = index==rawLex.length()-1;
					break;

				case COMMENT:
					eofToken = new Token();
					eofToken.setId("***lexical error: ");
					eofToken.setLexeme("un-expected end of file");
					break;

				case COM_ET1:
					eofToken = new Token();
					eofToken.setId("***lexical error: ");
					eofToken.setLexeme("un-expected end of file");

					endOfRawLex = index==rawLex.length()-1;
					break;

				case COM_ET2:
					eofToken = new Token();
					eofToken.setId("***lexical error: ");
					eofToken.setLexeme("un-expected end of file");

					endOfRawLex = index==rawLex.length()-1;
					break;

				case ENDTAGHEAD:
					t = new Token();
					t.setId(s.toString());
					t.setLexeme("</");
					displayToken(t);
					s = State.READY;
					break;

				case TAGIDENT:
					ident += rawLex.charAt(index);

					eofToken = new Token();
					eofToken.setId(s.toString() + "  ");
					eofToken.setLexeme("<" + ident);

					endOfRawLex = index==rawLex.length()-1;
					break;

				case GTHAN:
					t = new Token();
					t.setId(s.toString() + "     ");
					t.setLexeme(">");
					displayToken(t);
					s = State.READY;
					break;

				case COLON:
					t = new Token();
					t.setId(s.toString() + "     ");
					t.setLexeme(":");
					displayToken(t);
					s = State.READY;
					break;

				case SCOLON:
					t = new Token();
					t.setId(s.toString() + "    ");
					t.setLexeme(";");
					displayToken(t);
					s = State.READY;
					break;	

				case PERIOD:
					t = new Token();
					t.setId(s.toString() + "    ");
					t.setLexeme(".");
					displayToken(t);
					s = State.READY;
					break;

				case QUOTE:
					t = new Token();
					t.setId(s.toString() + "     ");
					t.setLexeme("'");
					displayToken(t);
					s = State.READY;
					break;

				case DQUOTE:
					t = new Token();
					t.setId(s.toString() + "    ");
					t.setLexeme("\"");
					displayToken(t);
					s = State.READY;
					break;

				case NUMBER_1_NOE:
					num += rawLex.charAt(index);

					eofToken = new Token();
					eofToken.setId("NUMBER    ");
					eofToken.setLexeme(num);

					endOfRawLex = index==rawLex.length()-1;
					break;

				case DECIMAL_NOE:
					num += rawLex.charAt(index);

					eofToken = new Token();
					eofToken.setId("***lexical error:");
					eofToken.setLexeme("badly formed number");
					eofBadNum = new Token();
					eofBadNum.setId("NUMBER" + "    ");
					eofBadNum.setLexeme(num);


					endOfRawLex = index==rawLex.length()-1;
					break;

				case NUMBER_2_NOE:
					num += rawLex.charAt(index);

					eofToken = new Token();
					eofToken.setId("NUMBER    ");
					eofToken.setLexeme(num);

					endOfRawLex = index==rawLex.length()-1;
					break;

				case NUMBER_1_WITHE:
					endOfRawLex = index==rawLex.length()-1;

					if (endOfRawLex) {
						t = new Token();
						t.setId("NUMBER    ");
						t.setLexeme(num);
						displayToken(t);
						num = "";
						endOfRawLex	= false;

						t.setId("IDENT     ");
						t.setLexeme(rawLex.charAt(index) + "");
						displayToken(t);

						s = State.READY;
					} else {
						num += rawLex.charAt(index);
					}
					break;

				case NUMBER_2_WITHE:
					num += rawLex.charAt(index);

					eofToken = new Token();
					eofToken.setId("NUMBER    ");
					eofToken.setLexeme(num);

					endOfRawLex = index==rawLex.length()-1;
					break;

				case NUMBER_WITHE_PLUSMINUS:
					endOfRawLex = index==rawLex.length()-1;

					num += rawLex.charAt(index);

					if (endOfRawLex) {
						t = new Token();
						t.setId("***lexical error:");
						t.setLexeme("badly formed number");
						displayToken(t);
						t.setId("NUMBER    ");
						t.setLexeme(num);
						displayToken(t);
						num = "";
						endOfRawLex	= false;

						s = State.READY;
					}

					break;

				case NUMBER_WITHE_END:
					num += rawLex.charAt(index);

					eofToken = new Token();
					eofToken.setId("NUMBER    ");
					eofToken.setLexeme(num);

					endOfRawLex = index==rawLex.length()-1;
					break;

				case BAD_NUMBER:
					
					num += rawLex.charAt(index);

					t = new Token();
					t.setId("***lexical error:");
					t.setLexeme("badly formed number");
					displayToken(t);
					t.setId("NUMBER    ");
					t.setLexeme(num);
					displayToken(t);
					num = "";
					endOfRawLex = false;
					s = State.READY;
					break;

				case IDENT:
					ident += rawLex.charAt(index);

					eofToken = new Token();
					eofToken.setId(s.toString() + "     ");
					eofToken.setLexeme(ident);

					endOfRawLex = index==rawLex.length()-1;
					break;
			}

			index++;

		}

	}

	public void displayToken(Token t) {
		System.out.println(t.getId() + " " + t.getLexeme());
	}
	

	public enum State {
		READY, PLUS, MINUS, DIVIDE, MODULO,  LPAREN, RPAREN, EQUALS, 
		MULT, EXP, 
		LTHAN, COM_HT1, COM_HT2, COMMENT, COM_ET1, COM_ET2, ENDTAGHEAD, TAGIDENT,
		GTHAN, COLON, COMMA, SCOLON, PERIOD, QUOTE, DQUOTE, 
		NUMBER_1_NOE, DECIMAL_NOE, NUMBER_2_NOE, NUMBER_1_WITHE, NUMBER_2_WITHE, NUMBER_WITHE_PLUSMINUS, NUMBER_WITHE_END, BAD_NUMBER, 
		IDENT, 
	}
}