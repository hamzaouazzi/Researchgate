package ma.ensa.researchgate.exceptions;


public class ResearchgateException extends RuntimeException {
    public ResearchgateException(String exMessage, Exception exception) {
        super(exMessage, exception);
    }

    public ResearchgateException(String exMessage) {
        super(exMessage);
    }
}
