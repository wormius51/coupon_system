package coupon.system.couponsystemweb.helpers;

import java.util.ArrayList;

public class ResponseObject {
	private boolean success;
	private String message;
	private ArrayList<?> content;

	public ResponseObject(boolean success, String message, ArrayList<?> content) {
		super();
		this.success = success;
		this.message = message;
		this.content = content;
	}

	public ResponseObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ArrayList<?> getContent() {
		return content;
	}

	public void setContent(ArrayList<?> content) {
		this.content = content;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + ((message == null) ? 0 : message.hashCode());
		result = prime * result + (success ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ResponseObject other = (ResponseObject) obj;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (message == null) {
			if (other.message != null)
				return false;
		} else if (!message.equals(other.message))
			return false;
		if (success != other.success)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ResponseObject [success=" + success + ", message=" + message + ", content=" + content + "]";
	}

}
