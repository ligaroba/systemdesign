package utils;

import enumerator.LoggingLevel;

public class LogHandler {
    public void writeInfo(String msg){
        System.out.println(LoggingLevel.INFO + " " + msg );
    }

    public void writeWarn(String msg){
        System.out.println(LoggingLevel.WARN + " " + msg);
    }
    public void writeError(String msg,Exception exception ){
        System.out.println(LoggingLevel.ERROR + " " + msg + " " + exception);
    }


}
