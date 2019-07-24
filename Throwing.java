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
     *           .map(Throwing.Function.of(this::someFunctionThatThrowsIOException)
     *           .forEach(System.out::println);
     * } catch (IOException e) {
     *     e.printStackTrace();
     * }
     * </pre></p>
     * @author dr6
     */
    @FunctionalInterface
    public interface Function<T, R, E extends Throwable> extends java.util.function.Function<T, R> {
        R throwingApply(T t) throws E;

        @Override
        default R apply(T t) {
            return ((Throwing.Function<T, R, RuntimeException>) this).throwingApply(t);
        }

        static <T, R, E extends Throwable> java.util.function.Function<T, R> of(Throwing.Function<T, R, E> tf) throws E {
            return tf;
        }
    }

    @FunctionalInterface
    public interface Supplier<T, E extends Throwable> extends java.util.function.Supplier<T> {
        T throwingGet() throws E;

        @Override
        default T get() {
            return ((Throwing.Supplier<T, RuntimeException>) this).throwingGet();
        }

        static <T, E extends Throwable> java.util.function.Supplier<T> of(Throwing.Supplier<T, E> ts) throws E {
            return ts;
        }
    }

    @FunctionalInterface
    public interface Consumer<T, E extends Throwable> extends java.util.function.Consumer<T> {
        void throwingAccept(T t);

        @Override
        default void accept(T t) {
            ((Throwing.Consumer<T, RuntimeException>) this).accept(t);
        }

        static <T, E extends Throwable> java.util.function.Consumer<T> of(Throwing.Consumer<T, E> tc) throws E {
            return tc;
        }
    }
}
