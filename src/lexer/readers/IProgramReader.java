package lexer.readers;

import lexer.exceptions.LexicalException;

public interface IProgramReader extends AutoCloseable {
    public char read() throws LexicalException;

    public int getColumn();

    public int getLineNumber();
}
