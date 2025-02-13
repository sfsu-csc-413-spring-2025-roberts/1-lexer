package tests.lexer;

import static org.junit.Assert.assertEquals;
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

public class LexerTest {

    @Test
    public void testEOF() {
        IProgramReader testReader = new TestSourceReader("");

        try {
            Lexer lexer = new Lexer(testReader);
            Token token = lexer.nextToken();

            assertEquals(TokenKind.EOF, token.getTokenKind());
        } catch (LexicalException e) {
            fail("Expected EOF token, but got LexicalException: " + e.getMessage());
        }
    }

    @Test
    public void testWhitespaceIsIgnored() {
        IProgramReader testReader = new TestSourceReader("\r\n\t   ");

        try {
            Lexer lexer = new Lexer(testReader);
            Token token = lexer.nextToken();

            assertEquals(TokenKind.EOF, token.getTokenKind());
        } catch (LexicalException e) {
            fail("Expected EOF token, but got LexicalException: " + e.getMessage());
        }
    }

    @Test
    public void testSingleLineComment() {
        IProgramReader testReader = new TestSourceReader("// This is a comment\nint x = 5");

        try {
            Lexer lexer = new Lexer(testReader);
            Token token = lexer.nextToken();

            assertEquals(TokenKind.IntType, token.getTokenKind());
            assertEquals("int", token.getLexeme());
        } catch (LexicalException e) {
            fail("Expected comment to be ignored, but got LexicalException: " + e.getMessage());
        }
    }

    @Test
    public void testEndOfLineComment() {
        IProgramReader testReader = new TestSourceReader("int // This is a comment\n");

        try {
            Lexer lexer = new Lexer(testReader);
            Token token = lexer.nextToken();

            assertEquals(TokenKind.IntType, token.getTokenKind());
            assertEquals("int", token.getLexeme());

            token = lexer.nextToken();
            assertEquals(TokenKind.EOF, token.getTokenKind());
        } catch (LexicalException e) {
            fail("Expected comment to be ignored, but got LexicalException: " + e.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("provideKnownLexemes")
    public void testKeywords(String source, String lexeme, TokenKind kind, int expectedStart, int expectedEnd) {
        IProgramReader testReader = new TestSourceReader(source);

        try {
            Lexer lexer = new Lexer(testReader);
            Token token = lexer.nextToken();

            assertEquals(kind, token.getTokenKind());
            assertEquals(lexeme, token.getLexeme());

            assertEquals(expectedStart, token.getStartColumn());
            assertEquals(expectedEnd, token.getEndColumn());
        } catch (LexicalException e) {
            fail("Expected token, but got LexicalException: " + e.getMessage());
        }
    }

    private static Stream<Arguments> provideKnownLexemes() {
        return Stream.of(
                Arguments.of(" bool ", "bool", TokenKind.BoolType, 1, 4),
                Arguments.of(" else ", "else", TokenKind.Else, 1, 4),
                Arguments.of(" if ", "if", TokenKind.If, 1, 2),
                Arguments.of(" int ", "int", TokenKind.IntType, 1, 3),
                Arguments.of(" program ", "program", TokenKind.Program, 1, 7),
                Arguments.of(" return ", "return", TokenKind.Return, 1, 6),
                Arguments.of(" then ", "then", TokenKind.Then, 1, 4),
                Arguments.of(" while ", "while", TokenKind.While, 1, 5),
                Arguments.of(" != ", "!=", TokenKind.NotEqual, 1, 2),
                Arguments.of(" & ", "&", TokenKind.And, 1, 1),
                Arguments.of(" * ", "*", TokenKind.Multiply, 1, 1),
                Arguments.of(" + ", "+", TokenKind.Plus, 1, 1),
                Arguments.of(" - ", "-", TokenKind.Minus, 1, 1),
                Arguments.of(" / ", "/", TokenKind.Divide, 1, 1),
                Arguments.of(" * ", "*", TokenKind.Multiply, 1, 1),
                Arguments.of(" < ", "<", TokenKind.Less, 1, 1),
                Arguments.of(" <= ", "<=", TokenKind.LessEqual, 1, 2),
                Arguments.of(" = ", "=", TokenKind.Assign, 1, 1),
                Arguments.of(" == ", "==", TokenKind.Equal, 1, 2),
                Arguments.of(" | ", "|", TokenKind.Or, 1, 1),
                Arguments.of(" ( ", "(", TokenKind.LeftParen, 1, 1),
                Arguments.of(" ) ", ")", TokenKind.RightParen, 1, 1),
                Arguments.of(" , ", ",", TokenKind.Comma, 1, 1),
                Arguments.of(" { ", "{", TokenKind.LeftBrace, 1, 1),
                Arguments.of(" } ", "}", TokenKind.RightBrace, 1, 1),
                Arguments.of(" x ", "x", TokenKind.Identifier, 1, 1),
                Arguments.of(" _x ", "_x", TokenKind.Identifier, 1, 2),
                Arguments.of(" myVar7 ", "myVar7", TokenKind.Identifier, 1, 6),
                Arguments.of(" muchlonger ", "muchlonger", TokenKind.Identifier, 1, 10),
                Arguments.of(" 0123456789 ", "0123456789", TokenKind.IntLit, 1, 10),
                Arguments.of(" 123 ", "123", TokenKind.IntLit, 1, 3));
    }
}
