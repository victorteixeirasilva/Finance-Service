package tech.inovasoft.inevolving.ms.finance.domain.exception;

public class DataBaseException extends Exception {
    public DataBaseException(String method) {
        super("Error in database, " + method);
    }

    public DataBaseException() {
        super("Error in database.");
    }
}
