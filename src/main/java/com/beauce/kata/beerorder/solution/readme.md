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
Create a record 'Pub' form the test: [OPTION] + [ENTER] > create `record`; with a name attribute.
```java
public record Pub(String name) {
    public Pub {
        if (null == name || name.isEmpty()) {
            throw new IllegalArgumentException("Pub name cannot be null or empty");
        }
    }
}
```
This is called a value object; key characteristics of a `value object`:
1. **Immutability**: The object’s state cannot be changed after creation.
2. **Equality** by Value: Two Value Objects are considered equal if their attributes are the same.
3. **No Identity**: Unlike entities, Value Objects do not have an identifier (ID).
4. **Encapsulation**: The logic related to the Value Object should be encapsulated within the class.

## Step 2: create a Beer object
Modify the test to use a `Beer` object instead of a `string` and a `double`:
```java
var pub = new Pub("O’Malley’s Pub");
var guinnessBeer = new Beer("Guinness", 5.0);
var kilkennyBeer = new Beer("Kilkenny", 4.5);
var invoice = service.generateInvoice(
        pub.name(),
        List.of(guinnessBeer.name(), kilkennyBeer.name()),
        List.of(10, 5),
        List.of(guinnessBeer.price(), kilkennyBeer.price())
);
```
From the test create a `Beer` object.
```java
public record Beer(String name, double price) {
    public Beer {
        if (null == name || name.isEmpty()) {
            throw new IllegalArgumentException("Beer name cannot be null or empty");
        }
        if (price <= 0) {
            throw new IllegalArgumentException("Beer price must be greater than 0");
        }
    }
}
```

## Step 3: create a BeerOrder object
The generate invoice method should take a `Pub` and list of `BeerOrder` object.
```java
List<BeerOrder> beerOrders = new ArrayList<>();
beerOrders.add(new BeerOrder(guinnessBeer, 10));
beerOrders.add(new BeerOrder(kilkennyBeer, 5));
var invoice = service.generateInvoice(pub, beerOrders);
```
Create the new method with the new signature:
```java
public String generateInvoice(Pub pub, List<BeerOrder> beerOrders) {
    return null;
}
```
Run the test to make it fail. First step of TDD.
Make it green as fast as possible:
```java
public String generateInvoice(Pub pub,
                              List<BeerOrder> beerOrders) {
    StringBuilder result = new StringBuilder("Invoice for %s:\n".formatted(pub.name()));
    for (var beerOrder : beerOrders) {
        result.append("%s - %d x %.1f€ = %.1f€\n".formatted(
                beerOrder.beer().name(),
                beerOrder.quantity(),
                beerOrder.beer().price(),
                beerOrder.totalPrice()));
    }
    result.append("Total: %.1f€".formatted(beerOrders.stream()
            .mapToDouble(BeerOrder::totalPrice)
            .sum()));
    return result.toString();
}
```
Next, can you make it better? Yes, let's refactor and create a first class collection with the `BeerOrder` list.
```java
var beerOrders = new BeerOrders(
        new BeerOrder(guinnessBeer, 10),
        new BeerOrder(kilkennyBeer, 5));
var invoice = service.generateInvoice(pub, beerOrders);
```
Create the `BeerOrders` class:
```java
public class BeerOrders {
    private final List<BeerOrder> beerOrders;

    public BeerOrders(BeerOrder... beerOrders) {
        if (beerOrders == null || beerOrders.length == 0) {
            throw new IllegalArgumentException("BeerOrders cannot be empty");
        }
        this.beerOrders = List.of(beerOrders);
    }

    public void forEach(Consumer<BeerOrder> action) {
        beerOrders.forEach(action);
    }

    public double getTotalPrice() {
        return beerOrders.stream()
                .mapToDouble(BeerOrder::totalPrice)
                .sum();
    }
}
```
Modify the `generateInvoice` method to use the `BeerOrders` object:
```java
public String generateInvoice(Pub pub,
                              BeerOrders beerOrders) {
    var result = new StringBuilder("Invoice for %s:\n".formatted(pub.name()));
    beerOrders.forEach(beerOrder -> result.append("%s - %d x %.1f€ = %.1f€\n".formatted(
            beerOrder.beer().name(),
            beerOrder.quantity(),
            beerOrder.beer().price(),
            beerOrder.totalPrice())));
    result.append("Total: %.1f€".formatted(beerOrders.getTotalPrice()));
    return result.toString();
}
```
## Step 4: Modify isOverBudget method
Change the test `shouldDetectOverBudgetOrders`, the `isOverBudget` method will take a `BeerOrders` object and a budget.
```java
@Test
void shouldDetectOverBudgetOrders() {
    var beerOrders = new BeerOrders(
            new BeerOrder(new Beer("Beer1", 6.0), 20),
            new BeerOrder(new Beer("Beer2", 5.5), 15));
    assertThat(service.isOverBudget(beerOrders, 100.0))
            .isTrue();
}
```
Modify the signature of the `isOverBudget` method to take a `BeerOrders` object and a budget.
```java
public boolean isOverBudget(BeerOrders beerOrders,
                            double budget) {
    return beerOrders.getTotalPrice() > budget;
}
```
Change the second test `shouldNotDetectOverBudgetOrders`:
```java
@Test
void shouldNotDetectOverBudgetOrders() {
    var beerOrders = new BeerOrders(
            new BeerOrder(new Beer("Beer1", 5.0), 5),
            new BeerOrder(new Beer("Beer2", 4.0), 2));
    assertThat(service.isOverBudget(beerOrders,100.0))
            .isFalse();
}
```


