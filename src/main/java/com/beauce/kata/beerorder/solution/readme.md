# Step by step refactoring
## Step 1: create a Pub object
The first parameter of the `generateInvoice` method, a string with value `O’Malley’s Pub`; the pub should be a class. So create a class for Pub.
Replace:
```java
var invoice = service.generateInvoice(
        "O’Malley’s Pub",
        ...
```
By
```java
var pub = new Pub("O’Malley’s Pub");
var invoice = service.generateInvoice(
        pub.name(),
        ...
```
So I create a record 'Pub' with a name attribute.
```java
public record Pub(String name) {
    public Pub {
        if (null == name || name.isEmpty()) {
            throw new IllegalArgumentException("Pub name cannot be null or empty");
        }
    }
}
```
This called a value object; key characteristics of a Value Object:
1. **Immutability**: The object’s state cannot be changed after creation.
2. **Equality** by Value: Two Value Objects are considered equal if their attributes are the same.
3. **No Identity**: Unlike entities, Value Objects do not have an identifier (ID).
4. **Encapsulation**: The logic related to the Value Object should be encapsulated within the class.


