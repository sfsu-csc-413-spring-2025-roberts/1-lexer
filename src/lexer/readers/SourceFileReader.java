package lexer.readers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;

import lexer.exceptions.LexicalException;

public class SourceFileReader implements IProgramReader {

    private BufferedReader reader;
    private int column;
    private int lineNumber;
    private char lastChar;

    public SourceFileReader(Path path) throws Exception {
        this(new BufferedReader(new FileReader(path.toFile())));
    }

    public SourceFileReader(BufferedReader reader) throws LexicalException {
        this.reader = reader;

        this.column = -1;
        this.lineNumber = 0;
        this.lastChar = ' ';
    }

    @Override
    public char read() throws LexicalException {
        if (this.lastChar == '\n') {
            this.lineNumber++;
            this.column = -1;
        }

        this.lastChar = this.getNextCharacter();

        return this.lastChar;
    }

    private char getNextCharacter() throws LexicalException {
        try {
            int character = this.reader.read();

            if (character == -1) {
                return '\0';
            }

            this.column++;
            return (char) character;
        } catch (IOException e) {
            throw new LexicalException("Error reading file: " + e.getMessage());
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
        this.reader.close();
    }
}
