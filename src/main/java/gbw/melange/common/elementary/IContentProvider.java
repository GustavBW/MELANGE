package gbw.melange.common.elementary;

@FunctionalInterface
public interface IContentProvider<T> {
    T fetch() throws Exception;
}
