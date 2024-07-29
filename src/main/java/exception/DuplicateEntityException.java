package exception;

public class DuplicateEntityException extends RuntimeException {
    private static final long serialVersionUID = 3063415409561892838L;

	public DuplicateEntityException(String message) {
        super(message);
    }
}
