# Java Test Data Builder

This is a library that helps generate test data for unit tests. The data structures are assumed to be an Immutabled data object. This means that the fields are `public final` and that each value corresponds to a constructor parameter. 

## Feature Hightlights

* Populates all fields of class
* Can return an Iterable of data objects
* Can set fields of the class
* Can rotate a number of different settings in the class

## Examples

Assume the following data class:

``` Java
public class Data {
  public final int field1;
  public final String field2;
  
  public Data(int field1, String field2){
    this.field1 = field1;
    this.field2 = field2;
  }
}
```

You can build a data object:

``` Java
  Data data = TestDataBuilder.create(Data.class).build();
  // field1 = 1
  // field2 = "2"
```

You can return an Iterator of objects:

``` Java
  Iterable<Data> data = TestDataBuilder.create(Data.class).build(3);
  // [
  //    {field1 = 1, field2 = "2"},
  //    {field1 = 1, field2 = "2"},
  //    {field1 = 1, field2 = "2"}
  //  ]
```

You can set fields inside the object:

``` Java
Data data = TestDataBuilder
                          .create(Data.class)
                          .set(TestDataBuilder.fieldOf(Data.class).field1,1234)
                          .build();
//field1 = 1234
//field2 = "2"
```

Fields values can rotate through the iterator:

``` Java
Iterable<Data> data = Data data = TestDataBuilder
                          .create(Data.class)
                          .set(TestDataBuilder.fieldOf(Data.class).field1,2,4,6)
                          .build(6);
  // [
  //    {field1 = 2, field2 = "2"},
  //    {field1 = 4, field2 = "2"},
  //    {field1 = 6, field2 = "2"},
  //    {field1 = 2, field2 = "2"},
  //    {field1 = 4, field2 = "2"},
  //    {field1 = 6, field2 = "2"}
  //  ]
```
