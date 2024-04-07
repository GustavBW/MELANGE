package gbw.melange.mesh.formatting.providers;

/**
 * Any function that takes in 2 params and returns some instance of type R
 * <pre>
 *     {@code (t, s, k) -> new R(t, s, k)  ||  R::new, where any 1 constructor in R has args (T, S, K)  }
 * </pre>
 * @param <T> in
 * @param <K> in
 * @param <S> in
 * @param <R> out
 */
@FunctionalInterface
public interface TriArgProvider<T,S,K,R>{
    R create(T t, S s, K k);
}
