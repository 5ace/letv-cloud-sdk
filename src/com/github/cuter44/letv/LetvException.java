package com.github.cuter44.letv;

public class LetvException extends RuntimeException
{
    private int code;
    private String message;

    public int getCode()
    {
        return(this.code);
    }

    public String getMessage()
    {
        return(this.message);
    }

    public LetvException(int aCode, String aMessage)
    {
        this.code = aCode;
        this.message = aMessage;

        return;
    }

    public LetvException(Throwable cause)
    {
        super(cause);

        return;
    }
}
