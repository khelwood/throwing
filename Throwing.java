/**
 * This class contains ways to trick the compiler into
 * letting checked exceptions through unchecked.
 * Use with caution.
 */
public class Throwing {

    private Throwing() {}

    /**
     * <p>
     * A function that can throw an exception.
     * Can be interpreted as a {@code Function}, even if it throws a checked exception.
     * This allows you to pass an exception-throwing function into a method that requires a {@code Function},
     * but you should still handle the exception in the surrounding scope.
     * </p><p>Example:
     * <pre>
     * try {
     *     Stream.of("Alpha", "Beta")
     *           .map(Throwing.function(this::someFunctionThatThrowsIOException)
     *           .forEach(System.out::println);
     * } catch (IOException e) {
     *     e.printStackTrace();
     * }
     * </pre></p>
     */
    @FunctionalInterface
    public interface Function<T, R, X extends Throwable> extends java.util.function.Function<T, R> {
        R throwingApply(T t) throws X;

        @Override
        default R apply(T t) {
            return ((Throwing.Function<T, R, RuntimeException>) this).throwingApply(t);
        }

    }

    @FunctionalInterface
    public interface Supplier<T, X extends Throwable> extends java.util.function.Supplier<T> {
        T throwingGet() throws X;

        @Override
        default T get() {
            return ((Throwing.Supplier<T, RuntimeException>) this).throwingGet();
        }
    }

    @FunctionalInterface
    public interface Consumer<T, X extends Throwable> extends java.util.function.Consumer<T> {
        void throwingAccept(T t) throws X;

        @Override
        default void accept(T t) {
            ((Throwing.Consumer<T, RuntimeException>) this).throwingAccept(t);
        }
    }

    public static <T, R, X extends Throwable> java.util.function.Function<T, R> function(Throwing.Function<T, R, X> tf) throws X {
        return tf;
    }

    public static <T, X extends Throwable> java.util.function.Supplier<T> supplier(Throwing.Supplier<T, X> ts) throws X {
        return ts;
    }

    public static <T, X extends Throwable> java.util.function.Consumer<T> consumer(Throwing.Consumer<T, X> tc) throws X {
        return tc;
    }
}
