/*
 * 文 件 名:  ErrorCodeException.java
 * 版    权:  Raisecom Technologies Co., Ltd. Copyright YYYY-YYYY,  All rights reserved
 * 描    述:  <描述>
 * 修 改 人:  donghu
 * 修改时间:  2017年8月17日
 * 跟踪单号:  <跟踪单号>
 * 修改单号:  <修改单号>
 * 修改内容:  <修改内容>
 */
package org.onap.usecaseui.server.exception;

/**
 * <异常处理>
 * <功能详细描述>
 * @author  donghu
 * @version  [版本号, 2017年8月17日]
 * @see  [相关类/方法]
 * @since  [产品/模块版本]
 */
public class ErrorCodeException extends Exception
{
    private static final long serialVersionUID = 3220072444842529499L;
    
    private int categoryCode = 0;
    
    private int errorCode = 1;
    
    private String[] arguments = null;
    
    private static String defaultText = null;
    
    public static void setDefaultText(String text)
    {
        defaultText = text;
    }
    
    public static String getDefaultText()
    {
        return defaultText;
    }
    
    public ErrorCodeException(int code, String debugMessage)
    {
        this(code, debugMessage, null);
    }
    
    public ErrorCodeException(int code, String debugMessage, String[] arguments)
    {
        super(debugMessage);
        this.errorCode = code;
        this.arguments = arguments;
    }
    
    public ErrorCodeException(Throwable source, int code)
    {
        this(source, code, (String[])null);
    }
    
    public ErrorCodeException(Throwable source, int code, String[] arguments)
    {
        super(source);
        this.errorCode = code;
        this.arguments = arguments;
    }
    
    public ErrorCodeException(Throwable source, int code, String debugMessage)
    {
        this(source, code, debugMessage, null);
    }
    
    public ErrorCodeException(Throwable source, int code, String debugMessage, String[] arguments)
    {
        super(debugMessage, source);
        this.errorCode = code;
        this.arguments = arguments;
    }
    
    public ErrorCodeException(int category, int code, String debugMessage)
    {
        this(category, code, debugMessage, null);
    }
    
    public ErrorCodeException(int category, int code, String debugMessage, String[] arguments)
    {
        super(debugMessage);
        this.categoryCode = category;
        this.errorCode = code;
        this.arguments = arguments;
    }
    
    public ErrorCodeException(Throwable source, int category, int code)
    {
        this(source, category, code, (String[])null);
    }
    
    public ErrorCodeException(Throwable source, int category, int code, String[] arguments)
    {
        super(source);
        this.categoryCode = category;
        this.errorCode = code;
        this.arguments = arguments;
    }
    
    public ErrorCodeException(Throwable source, int category, int code, String debugMessage)
    {
        this(source, category, code, debugMessage, null);
    }
    
    public ErrorCodeException(Throwable source, int category, int code, String debugMessage,
        String[] arguments)
    {
        super(debugMessage, source);
        this.categoryCode = category;
        this.errorCode = code;
        this.arguments = arguments;
    }
    
    public int getCategory()
    {
        return categoryCode;
    }
    
    public int getErrorCode()
    {
        return errorCode;
    }
    
    public String[] getArguments()
    {
        return arguments;
    }
}
