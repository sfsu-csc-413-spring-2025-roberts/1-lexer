package lexer;

import java.nio.file.Path;
import java.nio.file.Paths;

import lexer.daos.Symbol;
import lexer.daos.SymbolTable;
import lexer.daos.Token;
import lexer.daos.TokenKind;
import lexer.exceptions.LexicalException;
import lexer.readers.IProgramReader;
import lexer.readers.SourceFileReader;

public class Lexer implements ILexer {
    IProgramReader reader;

    private char current;
    private int start;
    private int end;

    public Lexer(Path filePath) throws Exception {
        this(new SourceFileReader(filePath));
    }

    public Lexer(IProgramReader reader) throws LexicalException {
        this.reader = reader;

        this.current = '\0';
        this.start = 0;
        this.end = -1;

        this.advance();
    }

    public Token nextToken() throws LexicalException {

        this.ignoreWhitespace();

        if (this.isAtEndOfFile()) {
            return new Token(new Symbol(TokenKind.EOF, "\0"), this.end, this.end);
        }

        this.startNewToken();

        if (Character.isDigit(this.current)) {
            return this.integerLiteral();
        }

        if (Character.isJavaIdentifierStart(this.current)) {
            return this.identifierOrKeyword();
        }

        return this.operatorOrSeparator();
    }

    private void advance() throws LexicalException {
        this.current = this.reader.read();
        this.end = this.reader.getColumn();
    }

    private void ignoreWhitespace() throws LexicalException {
        while (Character.isWhitespace(this.current)) {
            this.advance();
        }
    }

    private boolean isAtEndOfFile() {
        return this.current == '\0';
    }

    private void startNewToken() {
        this.start = this.end = this.reader.getColumn();
    }

    private Token createToken(Symbol symbol, int start, int end) {
        return new Token(symbol, start, end);
    }

    private Token createToken(String lexeme, TokenKind kind, int start, int end) {
        return createToken(SymbolTable.symbol(lexeme, kind), start, end);
    }

    private Token integerLiteral() throws LexicalException {
        String lexeme = "";

        while (!this.isAtEndOfFile() && Character.isDigit(this.current)) {
            lexeme += this.current;
            this.advance();
        }

        return createToken(lexeme, TokenKind.IntLit, this.start, this.end - 1);
    }

    private Token identifierOrKeyword() throws LexicalException {
        String lexeme = "";

        while (!this.isAtEndOfFile() && Character.isJavaIdentifierPart(this.current)) {
            lexeme += this.current;
            this.advance();
        }

        return createToken(lexeme, TokenKind.Identifier, this.start, this.end - 1);
    }

    private Token operatorOrSeparator() throws LexicalException {
        String singleCharacter = String.valueOf(this.current);

        try {
            this.advance();
        } catch (LexicalException e) {
            return this.singleCharacterOperator(singleCharacter);
        }

        String doubleCharacter = singleCharacter + this.current;
        Symbol possibleSymbol = SymbolTable.symbol(doubleCharacter, TokenKind.BogusToken);

        if (possibleSymbol == null) {
            return this.singleCharacterOperator(singleCharacter);
        } else if (possibleSymbol.getKind() == TokenKind.Comment) {
            this.ignoreComment();

            return this.nextToken();
        } else {
            this.advance();

            return createToken(possibleSymbol, this.start, this.end - 1);
        }
    }

    private Token singleCharacterOperator(String lexeme) throws LexicalException {
        Symbol symbol = SymbolTable.symbol(lexeme, TokenKind.BogusToken);

        if (symbol == null) {
            throw new LexicalException("Unrecognized symbol: " + lexeme);
        }

        // Handle the case where the single character operator is at the end of the file
        int modifier = this.current == '\0' ? 0 : 1;

        return this.createToken(symbol, this.start, this.end - modifier);
    }

    private void ignoreComment() throws LexicalException {
        while (!this.isAtEndOfFile() && this.current != '\n') {
            this.advance();
        }
    }

    @Override
    public Token anonymousIdentifierToken(String identifier) {
        throw new UnsupportedOperationException("Unimplemented method 'anonymousIdentifierToken'");
    }

    public static void main(String[] args) {
        if (args.length == 0) {

            System.out.println("Usage: java lexer.Lexer <filename>");
            return;

        }

        try {
            Path filePath = Paths.get(args[0]);
            Lexer lexer = new Lexer(filePath);
            Token token;

            while ((token = lexer.nextToken()).getTokenKind() != TokenKind.EOF) {
                System.out.println(token);
            }

            System.out.println();
            System.out.println(lexer);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}