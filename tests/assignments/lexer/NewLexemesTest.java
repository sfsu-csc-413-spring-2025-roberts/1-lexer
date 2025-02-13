package tests.assignments.lexer;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import lexer.Lexer;
import lexer.daos.Token;
import lexer.daos.TokenKind;
import lexer.exceptions.LexicalException;
import lexer.readers.IProgramReader;
import tests.helpers.TestSourceReader;

public class NewLexemesTest {

    @ParameterizedTest
    @MethodSource("provideAssignmentLexemes")
    public void testAssignmentLexemes(String source, String lexeme, TokenKind kind, int expectedStart,
            int expectedEnd) {
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

    private static Stream<Arguments> provideAssignmentLexemes() {
        return Stream.of(
                Arguments.of(" char ", "char", TokenKind.CharType, 1, 4),
                Arguments.of(" hex ", "hex", TokenKind.HexType, 1, 3),
                Arguments.of(" > ", ">", TokenKind.Greater, 1, 1),
                Arguments.of(" >= ", ">=", TokenKind.GreaterEqual, 1, 2),
                Arguments.of(" => ", "=>", TokenKind.To, 1, 2),
                Arguments.of(" from ", "from", TokenKind.From, 1, 4),
                Arguments.of(" step ", "step", TokenKind.Step, 1, 4));
    }
}
