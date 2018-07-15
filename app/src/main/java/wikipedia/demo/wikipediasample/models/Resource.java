package wikipedia.demo.wikipediasample.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import static wikipedia.demo.wikipediasample.models.Status.ERROR;
import static wikipedia.demo.wikipediasample.models.Status.LOADING;
import static wikipedia.demo.wikipediasample.models.Status.SUCCESS;

public class Resource<T> {

    @NonNull
    private final Status status;

    @Nullable
    private final Integer messageId;

    @Nullable
    private final T data;

    public Resource(@NonNull Status status, @Nullable T data, @Nullable Integer messageId) {
        this.status = status;
        this.data = data;
        this.messageId = messageId;
    }

    public static <T> Resource<T> success(@Nullable T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    public static <T> Resource<T> error(Integer msg) {
        return new Resource<>(ERROR, null, msg);
    }

    public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }

    @NonNull
    public Status getStatus() {
        return status;
    }

    @Nullable
    public Integer getMessageId() {
        return messageId;
    }

    @Nullable
    public T getData() {
        return data;
    }
}
