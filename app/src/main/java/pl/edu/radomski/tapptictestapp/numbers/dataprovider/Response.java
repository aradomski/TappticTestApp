package pl.edu.radomski.tapptictestapp.numbers.dataprovider;

/**
 * Created by radomskia on 01/06/2016.
 */
public class Response<T> {

    private T data;
    private Exception exception;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public Exception getException() {
        return exception;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }
}
