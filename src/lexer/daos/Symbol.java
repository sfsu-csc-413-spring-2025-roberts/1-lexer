package lexer.daos;

public class Symbol {

    private String lexeme;
    private TokenKind kind;

    public Symbol(TokenKind kind, String lexeme) {
        this.kind = kind;
        this.lexeme = lexeme;
    }

    public String getLexeme() {
        return lexeme;
    }

    public TokenKind getKind() {
        return kind;
    }
}
