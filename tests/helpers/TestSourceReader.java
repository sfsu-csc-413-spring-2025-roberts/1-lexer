package tests.helpers;

import lexer.readers.IProgramReader;

public class TestSourceReader implements IProgramReader {
    private String source;
    private int index;

    private int column;
    private int lineNumber;

    public TestSourceReader(String source) {
        this.source = source;

        this.index = -1;
        this.column = -1;
        this.lineNumber = 0;
    }

    @Override
    public char read() {
        if (index >= source.length()) {
            return '\0';
        }

        if (this.index > 0 && this.source.charAt(index) == '\n') {
            this.lineNumber++;
            this.column = -1;
        }

        this.column++;
        this.index++;

        if (this.index >= this.source.length()) {
            return '\0';
        } else {
            return this.source.charAt(index);
        }

    }

    @Override
    public int getColumn() {
        return this.column;
    }

    @Override
    public int getLineNumber() {
        return this.lineNumber;
    }

    @Override
    public void close() throws Exception {
        // no-op for test reader
    }
}
