package lexer;

import lexer.daos.Token;
import lexer.exceptions.LexicalException;

public interface ILexer {
    public Token nextToken() throws LexicalException;

    public Token anonymousIdentifierToken(String identifier);
}
