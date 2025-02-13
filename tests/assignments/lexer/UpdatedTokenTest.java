package tests.assignments.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import lexer.daos.Symbol;
import lexer.daos.Token;
import lexer.daos.TokenKind;

public class UpdatedTokenTest {

    @Test
    public void testColumnlessConstructor() {
        String testLexeme = "test";
        Symbol symbol = new Symbol(TokenKind.Divide, testLexeme);

        Token token = new Token(symbol);

        assertEquals(testLexeme, token.getLexeme());
        assertEquals(TokenKind.Divide, token.getTokenKind());
        assertEquals(-1, token.getStartColumn());
        assertEquals(-1, token.getEndColumn());
        assertEquals(-1, token.getLineNumber());
    }

    @Test
    public void testConstructorWithColumns() {
        String testLexeme = "test";
        Symbol symbol = new Symbol(TokenKind.Divide, testLexeme);

        int startColumn = 1;
        int endColumn = 2;

        Token token = new Token(symbol, startColumn, endColumn);

        assertEquals(testLexeme, token.getLexeme());
        assertEquals(TokenKind.Divide, token.getTokenKind());
        assertEquals(startColumn, token.getStartColumn());
        assertEquals(endColumn, token.getEndColumn());
        assertEquals(-1, token.getLineNumber());
    }

    @Test
    public void testConstructorWithLineNumber() {
        String testLexeme = "test";
        Symbol symbol = new Symbol(TokenKind.Divide, testLexeme);

        int startColumn = 1;
        int endColumn = 2;
        int lineNumber = 42;

        Token token = new Token(symbol, startColumn, endColumn, lineNumber);

        assertEquals(testLexeme, token.getLexeme());
        assertEquals(TokenKind.Divide, token.getTokenKind());
        assertEquals(startColumn, token.getStartColumn());
        assertEquals(endColumn, token.getEndColumn());
        assertEquals(lineNumber, token.getLineNumber());
    }

    @Test
    public void testStringRepresentation() {
        String testLexeme = "test";
        Symbol symbol = new Symbol(TokenKind.Divide, testLexeme);

        int startColumn = 1;
        int endColumn = 2;
        int lineNumber = 42;

        Token token = new Token(symbol, startColumn, endColumn, lineNumber);

        String expectedString = String.format("%-20s left: %-8s right: %-8s line: %-8d %s",
                testLexeme, startColumn, endColumn, lineNumber, TokenKind.Divide);
        assertEquals(expectedString, token.toString());
    }
}
