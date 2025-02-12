package tests.lexer.daos;

import static org.junit.Assert.assertEquals;

import org.junit.jupiter.api.Test;

import lexer.daos.Symbol;
import lexer.daos.TokenKind;

public class SymbolTest {

    @Test
    public void testGetLexeme() {
        String testLexeme = "test";
        Symbol testSymbol = new Symbol(TokenKind.Assign, testLexeme);

        assertEquals(testLexeme, testSymbol.getLexeme());
    }

    @Test
    public void testGetKind() {
        TokenKind testKind = TokenKind.Assign;
        Symbol testSymbol = new Symbol(testKind, "test");

        assertEquals(testKind, testSymbol.getKind());
    }
}
