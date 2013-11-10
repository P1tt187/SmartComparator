SmartComparator
===============

A simple way to generate a comparator in Java

------------------------

##Description
SmartComparator gives you the ability to generate a java.util.Comparator for your class. You don't have to write your own Comparator or implement Comparable anymore. Just create a new SmartComparator and tell it which attributes or methods are used for comparison.

Sorting with multiple attributes is also possible.

##Compile
You need JDK 1.7 and Maven to compile the project.
```shell
    mvn clean
    mvn compile
```
##Usage
There are multiple ways to the use SmartComparator.

###Using the constructor
```Java
    SmartComparator<YourDataClass> sc =
           new SmartComparator<>(YourDataClass.class,
            new MethodNameGenerator("getVal1")
             .add("getVal2",SortType.DESC).getList());
```

Using MethodNameGenerator gives you a comfortable way to generate a list or an array of MethodNameRecord, which are used by the SmartComparator.
The String arguments in MethodNameGenerator are the name of the methods which should be used to compare your objects. SortType is an optional argument. Default is ascended but you can switch to descended, which will reverse the order for the given method.

The order of the parameters specify how the comparator should work. In this case it will first compare val1 of the class. If the values are equal it uses val2 to compare objects.

It is also possible to change the sorting behaviour.
```Java
    sc.changeSorting(new MethodNameGenerator("getVal2","getVal1").getList());
```
Now the Comparator will first compare against val2 and if it's equal against val1.

##Using Anotations
Example: you have this class
```Java
public class TestStringObject {

    @CompareCriteria(priority = 2)
    private String val;

    private int val2;

   @CompareCriteria(priority = 1, sortType=SortType.DESC)
    public int getVal2() {

        return val2;
    }

    public TestStringObject(String val, int val2) {
        this.val = val;
        this.val2 = val2;
    }
   
    public String getVal() {
        return val;
    }  
}
```
You can annotate every field or mehtod of the class with  `@CompareCriteria`. This will tell SmartComparator(sc) to use it in the `compare` method

**Note:** if you have multiple values to compare you have to specify the priority of these values. The higher the value the more important is it to compare. 
To use it create the Comparator just like this:
```Java
    Comparator<TestStringObject> sc = new SmartComparator(TestStringObject.class);
```
##NamedSorts
NamedSorts gives you the ability to predefine multiple sets of sorting criteriars and allows you easly to switch between them.

Example:
```Java
@NamedSorts({
        @NamedSort(
                name = "allASC",
                methodNames = {
                        @MethodName(name = "getVal"),
                        @MethodName(name = "getName")
                }),
        @NamedSort(
                name = "mixed",
                methodNames = {
                        @MethodName(name = "getName", sortType = SortType.DESC),
                        @MethodName(name = "getVal")
                }
        )
})
public class TestNamedSortsObject {

    Integer val;
    String name;
   
    public Integer getVal() {
        return val;
    }

    public String getName() {
        return name;
    }  
}
```

NamedSorts expects an array of NamedSort as argument. Each NamedSort has a name and contains an array of MethodName. Each MethodName has also a name and a sortType als optional value. The order of the MethodNames defines the behaviour for sc.

To use it create the SmartComparator just like this:
```Java
    SmartComparator sc = new SmartComparator(TestNamedSortsObject.class, "allASC");
```

If you want to change the sorting behaviour just use the `changeSorting` method:
```Java
    sc.changeSorting("mixed");
```

#More information
For more information's about this project look at http://www.penta-it.com/
