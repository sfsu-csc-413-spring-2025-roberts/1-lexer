package tests.lexer.readers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.BufferedReader;
import java.io.StringReader;

import org.junit.jupiter.api.Test;

import lexer.readers.SourceFileReader;

public class SourceFileReaderTest {

    @Test
    public void testEmptyFile() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(""));

        try (SourceFileReader sourceReader = new SourceFileReader(bufferedReader)) {
            assertEquals('\0', sourceReader.read());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testEmptyFileWithMultipleReads() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader(""));

        try (SourceFileReader sourceReader = new SourceFileReader(bufferedReader)) {
            assertEquals('\0', sourceReader.read());
            assertEquals('\0', sourceReader.read());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSingleCharacterFile() {
        BufferedReader bufferedReader = new BufferedReader(new StringReader("a"));

        try (SourceFileReader sourceReader = new SourceFileReader(bufferedReader)) {
            assertEquals('a', sourceReader.read());
            assertEquals('\0', sourceReader.read());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testMultiLineFile() {
        String multiLineFile = "abc\ndef";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(multiLineFile));

        try (SourceFileReader sourceReader = new SourceFileReader(bufferedReader)) {
            for (int i = 0; i < multiLineFile.length(); i++) {
                assertEquals(multiLineFile.charAt(i), sourceReader.read());
            }

            assertEquals('\0', sourceReader.read());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testLineNumberSingleLine() {
        String singleLineFile = "abc";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(singleLineFile));

        try (SourceFileReader sourceReader = new SourceFileReader(bufferedReader)) {
            assertEquals(0, sourceReader.getLineNumber());

            for (int i = 0; i < singleLineFile.length(); i++) {
                sourceReader.read();
                assertEquals(0, sourceReader.getLineNumber());
            }
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testLineNumberMultiLine() {
        String multiLineFile = "abc\ndef\n\nj";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(multiLineFile));

        try (SourceFileReader sourceReader = new SourceFileReader(bufferedReader)) {
            sourceReader.read(); // a
            assertEquals(0, sourceReader.getLineNumber());

            sourceReader.read(); // b
            assertEquals(0, sourceReader.getLineNumber());

            sourceReader.read(); // c
            assertEquals(0, sourceReader.getLineNumber());

            sourceReader.read(); // \n
            assertEquals(0, sourceReader.getLineNumber());

            sourceReader.read(); // d
            assertEquals(1, sourceReader.getLineNumber());

            sourceReader.read(); // e
            assertEquals(1, sourceReader.getLineNumber());

            sourceReader.read(); // f
            assertEquals(1, sourceReader.getLineNumber());

            sourceReader.read(); // \n
            assertEquals(1, sourceReader.getLineNumber());

            sourceReader.read(); // \n
            assertEquals(2, sourceReader.getLineNumber());

            sourceReader.read(); // j
            assertEquals(3, sourceReader.getLineNumber());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testColumnMultiLine() {
        String multiLineFile = "ab\nd\n\njk";

        BufferedReader bufferedReader = new BufferedReader(new StringReader(multiLineFile));

        try (SourceFileReader sourceReader = new SourceFileReader(bufferedReader)) {
            sourceReader.read(); // a
            assertEquals(0, sourceReader.getColumn());

            sourceReader.read(); // b
            assertEquals(1, sourceReader.getColumn());

            sourceReader.read(); // \n
            assertEquals(2, sourceReader.getColumn());

            sourceReader.read(); // d
            assertEquals(0, sourceReader.getColumn());

            sourceReader.read(); // \n
            assertEquals(1, sourceReader.getColumn());

            sourceReader.read(); // \n
            assertEquals(0, sourceReader.getColumn());

            sourceReader.read(); // j
            assertEquals(0, sourceReader.getColumn());

            sourceReader.read(); // k
            assertEquals(1, sourceReader.getColumn());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
