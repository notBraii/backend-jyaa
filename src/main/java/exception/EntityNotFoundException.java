package exception;

public class EntityNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1675675198200800817L;

	public EntityNotFoundException(String message) {
        super(message);
    }
}
