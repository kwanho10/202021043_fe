package kr.ac.kku.cs.wp.kwanho10.tools.message;

public class MessageException extends RuntimeException {
  
	private static final long serialVersionUID = 1L;

    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
