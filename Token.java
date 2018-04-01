public class Token {
	String id;
	String lexeme;

	public Token(String id, String lexeme) {
		this.id = id;
		this.lexeme = lexeme;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return this.id;
	}

	public void setLexeme(String lexeme) {
		this.lexeme = lexeme;

	}

	public String getLexeme() {
		return lexeme;
	}
}