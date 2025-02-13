package tests.assignments.lexer;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.Stream;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lexer.Lexer;
import lexer.daos.Token;
import lexer.daos.TokenKind;
import lexer.exceptions.LexicalException;
import lexer.readers.IProgramReader;
import tests.helpers.TestSourceReader;

public class UpdatedLexerTest {

    @ParameterizedTest
    @MethodSource("provideValidCharacters")
    public void testAssignmentCharacterLexemes(String source, String lexeme, int expectedStart,
            int expectedEnd) {
        IProgramReader testReader = new TestSourceReader(source);

        try {
            Lexer lexer = new Lexer(testReader);
            Token token = lexer.nextToken();

            assertEquals(TokenKind.CharLit, token.getTokenKind());
            assertEquals(lexeme, token.getLexeme());

            assertEquals(expectedStart, token.getStartColumn());
            assertEquals(expectedEnd, token.getEndColumn());
        } catch (LexicalException e) {
            fail("Expected character token, but got LexicalException: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideInvalidCharacters")
    public void testInvalidCharacterLexemes(String source) {
        IProgramReader testReader = new TestSourceReader(source);

        LexicalException exception = assertThrows(LexicalException.class, () -> {
            Lexer lexer = new Lexer(testReader);
            lexer.nextToken();
        });

        assertEquals("Invalid character literal: " + source.trim().substring(0, 3), exception.getMessage());
    }

    @Test
    public void testUnterminatedCharLiteral() {
        String source = " \"";

        IProgramReader testReader = new TestSourceReader(source);

        LexicalException exception = assertThrows(LexicalException.class, () -> {
            Lexer lexer = new Lexer(testReader);
            lexer.nextToken();
        });

        assertEquals("Unterminated character literal", exception.getMessage());
    }

    @Test
    public void testUnterminatedCharLiteralEOF() {
        String source = " \"a";

        IProgramReader testReader = new TestSourceReader(source);

        LexicalException exception = assertThrows(LexicalException.class, () -> {
            Lexer lexer = new Lexer(testReader);
            lexer.nextToken();
        });

        assertEquals("Unterminated character literal", exception.getMessage());
    }

    @ParameterizedTest
    @MethodSource("provideValidHex")
    public void testAssignmentHexLexemes(String source, String lexeme, int expectedStart, int expectedEnd) {
        IProgramReader testReader = new TestSourceReader(source);

        try {
            Lexer lexer = new Lexer(testReader);
            Token token = lexer.nextToken();

            assertEquals(TokenKind.HexLit, token.getTokenKind());
            assertEquals(lexeme, token.getLexeme());

            assertEquals(expectedStart, token.getStartColumn());
            assertEquals(expectedEnd, token.getEndColumn());
        } catch (LexicalException e) {
            fail("Expected hex token, but got LexicalException: " + e.getMessage());
        }
    }

    private static Stream<Arguments> provideValidCharacters() {
        return Stream.of(
                Arguments.of(" \"a\" ", "\"a\"", 1, 3),
                Arguments.of(" \"0\" ", "\"0\"", 1, 3));
    }

    private static Stream<Arguments> provideInvalidCharacters() {
        return Stream.of(
                Arguments.of(" \"a1\" "),
                Arguments.of(" \"0b\" "));
    }

    private static Stream<Arguments> provideValidHex() {
        return Stream.of(
                Arguments.of(" 0x1a2F ", "0x1a2F", 1, 6),
                Arguments.of(" 0Xa ", "0Xa", 1, 3),
                Arguments.of(" 0x0123456789aBcDeF ", "0x0123456789aBcDeF", 1, 18));
    }
}
