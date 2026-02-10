The Java `Optional` class is a container class that represents a single value of either type `T` or no value at all (i.e., `null`). It was introduced in Java 8 as part of the Java Stream API to help avoid `NullPointerExceptions` and make your code more robust.

Here are some key features and methods of the Java `Optional` class:

### Why use Optional?

*   To avoid `NullPointerException`: When a method returns `null`, it can be difficult to handle, especially when chaining multiple operations together. Using `Optional` allows you to explicitly indicate that a value may or may not exist.
*   To improve code readability: By using `Optional`, you can clearly express the intent of your code and make it easier for others (and yourself) to understand.

### Key Methods

1.  **of(T t)**: Creates an `Optional` containing the specified non-null value (`t`).
2.  **ofNullable(T t)**: Creates an `Optional` that may contain the specified value or be empty if the value is null.
3.  **empty()**: Returns a new, empty instance of Optional.
4.  **isPresent()**: Returns true if this Optional contains a non-null element (`t`) or false otherwise.
5.  **get()**: If a value is present in this Optional, returns it; otherwise throws `NoSuchElementException`.
6.  **`ifPresent(Consumer<?  super T> consumer)`**: If a value is present in this Optional, performs the specified action with that value.

### Common Operations

1.  **`map(Function<? super T, ? extends U> mapper)`**: Applies a transformation function to the contained value if it's present.
2.  **`filter(Predicate<? super T> predicate)`**: Returns an `Optional` containing the original value if it matches the specified condition.
3.  **`flatMap(Function<? super T, ? extends Optional<U>> mapper)**`: Similar to `map`, but applies a function that returns another `Optional`.
4.  **orElse(T other)**: If this `Optional` contains no value or no non-null value, returns the provided default value (`other`).
5.  **`orElseGet(Supplier<? extends T> other)**`: Returns the contained value if present; otherwise uses a specified supplier function to provide a default value.

### Example Usage

```java
import java.util.Optional;

public class Main {
    public static void main(String[] args) {
        // Creating an Optional with a non-null value
        Optional<String> name = Optional.of("John");
        
        // Using orElse for a default value if present
        String result1 = name.orElse("Unknown");  // Output: John
        
        // Using get() to retrieve the value (throws exception if empty)
        try {
            String result2 = name.get();
            System.out.println(result2);  // Output: John
        } catch (NoSuchElementException e) {
            System.out.println("No value present");
        }
        
        // Creating an Optional with a null value using ofNullable()
        Optional<String> optionalName = Optional.ofNullable(null);
        
        // Using orElse for a default value if empty
        String result3 = optionalName.orElse("Unknown");  // Output: Unknown
        
        // Using isPresent() to check if the value is present
        boolean isPresent1 = name.isPresent();
        System.out.println(isPresent1);  // Output: true
        
        // Using get() on an empty Optional throws an exception
        try {
            String result4 = optionalName.get();  // Throws NoSuchElementException
        } catch (NoSuchElementException e) {
            System.out.println("No value present");
        }
    }
}
```

By using the `Optional` class effectively, you can write more robust and expressive code in Java.