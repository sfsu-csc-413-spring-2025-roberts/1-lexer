package tests.lexer.daos;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import lexer.daos.Symbol;
import lexer.daos.Token;
import lexer.daos.TokenKind;

public class TokenTest {
    @Test
    public void testColumnlessConstructor() {
        String testLexeme = "test";
        Symbol symbol = new Symbol(TokenKind.Divide, testLexeme);

        Token token = new Token(symbol);

        assertEquals(testLexeme, token.getLexeme());
        assertEquals(TokenKind.Divide, token.getTokenKind());
        assertEquals(-1, token.getStartColumn());
        assertEquals(-1, token.getEndColumn());
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
    }

    @Test
    public void testGetLexeme() {
        String testLexeme = "test";
        Symbol symbol = new Symbol(TokenKind.Divide, testLexeme);

        Token token = new Token(symbol);

        assertEquals(testLexeme, token.getLexeme());
    }

    @Test
    public void testGetTokenKind() {
        TokenKind testKind = TokenKind.Divide;
        Symbol symbol = new Symbol(testKind, "test");

        Token token = new Token(symbol);

        assertEquals(testKind, token.getTokenKind());
    }

    @Test
    public void testGetStartColumn() {
        int testStartColumn = 1;
        Symbol symbol = new Symbol(TokenKind.Divide, "test");

        Token token = new Token(symbol, testStartColumn, 2);

        assertEquals(testStartColumn, token.getStartColumn());
    }

    @Test
    public void testGetEndColumn() {
        int testEndColumn = 2;
        Symbol symbol = new Symbol(TokenKind.Divide, "test");

        Token token = new Token(symbol, 1, testEndColumn);

        assertEquals(testEndColumn, token.getEndColumn());
    }
}
