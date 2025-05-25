package tech.inovasoft.inevolving.ms.finance.domain.exception;

public class NotFoundTransactionException extends Exception {
    public NotFoundTransactionException() {
        super("Transaction not found");
    }
}
