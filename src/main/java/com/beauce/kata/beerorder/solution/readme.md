# Step by step refactoring
## Step 1: create a Pub object
The first parameter of the generateInvoice method is a string with the value "O’Malley’s Pub", which represents a pub. Instead of using a plain string, the pub should be a class. So, let’s create a Pub class.

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
Create a `Pub` record from the test: Press [OPTION] + [ENTER] and select `Create record`; ensure it has a `name` attribute.
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
Create a Beer `record`:
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
The `generateInvoice` method should take a `Pub` and a list of `BeerOrder` objects.
```java
List<BeerOrder> beerOrders = new ArrayList<>();
beerOrders.add(new BeerOrder(guinnessBeer, 10));
beerOrders.add(new BeerOrder(kilkennyBeer, 5));
var invoice = service.generateInvoice(pub, beerOrders);
```
Create the new method with the updated signature:
```java
public String generateInvoice(Pub pub, List<BeerOrder> beerOrders) {
    return null;
}
```
Run the test to make it fail (first step of TDD).
Make it pass as quickly as possible:
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
### Refactoring: Introduce a BeerOrders Collection
To improve code structure, create a first-class collection for `BeerOrder` objects.
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
Modify `generateInvoice` method to use `BeerOrders`:
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
Change the test `shouldDetectOverBudgetOrders` and `shouldNotDetectOverBudgetOrders`, the `isOverBudget` method should take a `BeerOrders` object and a `budget`.
```java
@Test
void shouldDetectOverBudgetOrders() {
    var beerOrders = new BeerOrders(
            new BeerOrder(new Beer("Beer1", 6.0), 20),
            new BeerOrder(new Beer("Beer2", 5.5), 15));
    assertThat(service.isOverBudget(beerOrders, 100.0))
            .isTrue();
}

@Test
void shouldNotDetectOverBudgetOrders() {
    var beerOrders = new BeerOrders(
            new BeerOrder(new Beer("Beer1", 5.0), 5),
            new BeerOrder(new Beer("Beer2", 4.0), 2));
    assertThat(service.isOverBudget(beerOrders, 100.0))
            .isFalse();
}
```
Modify `isOverBudget`:
```java
public boolean isOverBudget(BeerOrders beerOrders,
                            double budget) {
    return beerOrders.getTotalPrice() > budget;
}
```
## Step 5: create an Invoice class to refactor the generateInvoice method
Modify `generateInvoice` method to use an `Invoice`:
```java
    public String generateInvoice(Pub pub,
                                  BeerOrders beerOrders) {
        return new Invoice(pub, beerOrders)
                .generate();
    }
```
Create the `Invoice` record:
```java
public record Invoice(Pub pub, BeerOrders beerOrders) {
    public Invoice {
        if (null == pub) {
            throw new IllegalArgumentException("Pub cannot be null");
        }
        if (null == beerOrders) {
            throw new IllegalArgumentException("BeerOrders cannot be null");
        }
    }

    public String generate() {
        return null;
    }
}
```
Implement the `generate` method; use a string a delegate the formatting to `BeerOrders`.
```java
public String generate() {
    return """
            Invoice for %s:
            %s
            Total: %s€""".formatted(pub.name(), beerOrders.toString(), beerOrders.getTotalPrice());
}
```
Implement the `toString` method in `BeerOrders` class:
```java
@Override
public String toString() {
    return beerOrders.stream()
            .map(Record::toString)
            .collect(Collectors.joining("\n"));
}
```
Implement the `toString` method in `BeerOrder` class:
```java
@Override
public String toString() {
    return "%s - %d x %s€ = %s€"
            .formatted(beer.name(), quantity, beer.price(), totalPrice());
}
```
## Step 6: create a `UnitPrice` and `Quantity` class
Modify test `shouldDetectOverBudgetOrders` to use the `UnitPrice` and `Quantity` classes.
```java
var guinnessBeer = new Beer("Guinness", new UnitPrice(5.0));
var kilkennyBeer = new Beer("Kilkenny", new UnitPrice(4.5));
var beerOrders = new BeerOrders(
        new BeerOrder(guinnessBeer, new Quantity(10)),
        new BeerOrder(kilkennyBeer, new Quantity(5)));
```
Create the `Quantity` class:
```java
public record Quantity(int quantity) {
    public Quantity {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
    }
}
```
Like this we can delegate the verification of the quantity to the `Quantity` class.
Then, we do the same for the `UnitPrice` class; so that we can delegate the multiplication of the quantity by the price to the `UnitPrice` class.
```java
public record UnitPrice(double value) {
    public UnitPrice {
        if (value <= 0) {
            throw new IllegalArgumentException("Price must be greater than 0");
        }
    }

    public double multiplyBy(Quantity quantity) {
        return BigDecimal.valueOf(value())
                .multiply(BigDecimal.valueOf(quantity.value()))
                .setScale(1, RoundingMode.HALF_UP)
                .doubleValue();
    }
}
```






