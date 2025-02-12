package tests.helpers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

public class TestSourceReaderTest {

    @Test
    public void testEmptyFile() {
        try (TestSourceReader sourceReader = new TestSourceReader("")) {
            assertEquals('\0', sourceReader.read());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testEmptyFileWithMultipleReads() {
        try (TestSourceReader sourceReader = new TestSourceReader("")) {
            assertEquals('\0', sourceReader.read());
            assertEquals('\0', sourceReader.read());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testSingleCharacterFile() {
        try (TestSourceReader sourceReader = new TestSourceReader("a")) {
            assertEquals('a', sourceReader.read());
            assertEquals('\0', sourceReader.read());
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }

    @Test
    public void testMultiLineFile() {
        String multiLineFile = "abc\ndef";

        try (TestSourceReader sourceReader = new TestSourceReader(multiLineFile)) {
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

        try (TestSourceReader sourceReader = new TestSourceReader(singleLineFile)) {
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

        try (TestSourceReader sourceReader = new TestSourceReader(multiLineFile)) {
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

        try (TestSourceReader sourceReader = new TestSourceReader(multiLineFile)) {
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
