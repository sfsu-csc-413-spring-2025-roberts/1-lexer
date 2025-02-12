package lexer.daos;

public class Token {
    private Symbol symbol;
    private int startColumn;
    private int endColumn;

    public Token(Symbol symbol) {
        this(symbol, -1, -1);
    }

    public Token(Symbol symbol, int startColumn, int endColumn) {
        this.symbol = symbol;
        this.startColumn = startColumn;
        this.endColumn = endColumn;
    }

    public String getLexeme() {
        return this.symbol.getLexeme();
    }

    public TokenKind getTokenKind() {
        return this.symbol.getKind();
    }

    public int getStartColumn() {
        return this.startColumn;
    }

    public int getEndColumn() {
        return this.endColumn;
    }

    @Override
    public String toString() {
        return String.format("%-15s (left: %3d, right: %3d)", this.symbol.getLexeme(), this.startColumn,
                this.endColumn);
    }
}
