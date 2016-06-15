package consumer;

/**
 * Created by Administrator on 2016/4/26.
 */
public interface Consumer<T> {
    void accept(T t);
}
