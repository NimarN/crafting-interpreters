package com.craftinginterpreters.lox;

import java.util.ArrayList;
import java.util.List;

import static com.craftinginterpreters.lox.TokenType.*;

/**
 * 
 * Scanner
 */
class Scanner {
    // raw source code will be stored as a string
    private final String source;
    private final List<Token> tokens = new ArrayList<>();
    // points to the first character of the lexeme being scanned
    private int start = 0;
    // points at the current character being considered
    private int current = 0;
    // trakcs what source line current is on so we can produce tokens that now their location
    private int line = 1;

    Scanner(String source) {
        this.source = source;
    }

    List<Token> scanTokens() {
        while (!isAtEnd()) {
            // we are at the beginning of the next lexeme
            start = current;
            scanToken();
        }

        tokens.add(new Token(EOF, "", null, line));
        return tokens;
    }

    private void scanToken() {
        char c = advance();
        switch (c) {
            case '(': addToken(LEFT_PAREN); break;
            case ')': addToken(RIGHT_PAREN); break;
            case '{': addToken(LEFT_BRACE); break;
            case '}': addToken(RIGHT_PAREN); break;
            case ',': addToken(COMMA); break;
            case '.': addToken(DOT); break;
            case '-': addToken(MINUS); break;
            case '+': addToken(PLUS); break;
            case ';': addToken(SEMICOLON); break;
            case '*': addToken(STAR); break;

            default:
                Lox.error(line, "Unexpected character.");
                break;
        }

    }
    
    /**
     * 
     * @return wether we have consumed all characters of source
     */
    private boolean isAtEnd() {
        return current >= source.length();
    }   

    /**
     * 
     * @return the next character in the source file
     */
    private char advance() {
        return source.charAt(current++);
    }

    /**
     * Grabs the text of the current lexeme and creates a new token for it
     * @param type
     */
    private void addToken(TokenType type) {
        addToken(type, null);
    }

    private void addToken(TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}
