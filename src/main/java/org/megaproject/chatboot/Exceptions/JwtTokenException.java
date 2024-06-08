package org.megaproject.chatboot.Exceptions;

public class JwtTokenException extends RuntimeException{
    public JwtTokenException(String message) {
        super(message);
    }
}
