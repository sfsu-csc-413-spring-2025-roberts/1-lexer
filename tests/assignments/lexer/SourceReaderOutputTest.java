package tests.assignments.lexer;

import static org.junit.Assert.fail;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.List;

import org.junit.jupiter.api.Test;

import lexer.Lexer;
import lexer.daos.TokenKind;
import lexer.exceptions.LexicalException;
import lexer.readers.IProgramReader;
import lexer.readers.SourceFileReader;

public class SourceReaderOutputTest {

    @Test
    public void testSourceReaderOutputSingleLine() {
        String source = "int x = 5";

        try {
            IProgramReader testReader = new SourceFileReader(new BufferedReader(new StringReader(source)));
            Lexer lexer = new Lexer(testReader);

            while (lexer.nextToken().getTokenKind() != TokenKind.EOF) {
                // Consume tokens until EOF
            }

            assertEquals(String.format("%3d: %s", 1, source), lexer.toString());
        } catch (LexicalException e) {
            fail("Expected EOF token, but got LexicalException: " + e.getMessage());
        }
    }

    @Test
    public void testSourceReaderOutputMultiLine() {
        List<String> source = List.of(
                "program { int x char c",
                "  x = 5",
                "  c = \"a\"",
                "  // This is a comment",
                "  x = write(x)",
                "}");

        try {
            IProgramReader testReader = new SourceFileReader(
                    new BufferedReader(new StringReader(String.join(System.lineSeparator(), source))));
            Lexer lexer = new Lexer(testReader);

            while (lexer.nextToken().getTokenKind() != TokenKind.EOF) {
                // Consume tokens until EOF
            }

            String expectedOutput = String.join(System.lineSeparator(),
                    source.stream().map(line -> String.format("%3d: %s", source.indexOf(line) + 1, line)).toList());

            assertEquals(expectedOutput, lexer.toString());
        } catch (LexicalException e) {
            fail("Expected EOF token, but got LexicalException: " + e.getMessage());
        }
    }
}
