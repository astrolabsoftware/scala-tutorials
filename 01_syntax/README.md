# Scala syntax

A rapid overview of the Scala syntax to survive in a hostile environment:

- What you will see: (im)mutable, type declaration, class, object, heritage, and documentation.
- What you won't see by lack of time (non-exhaustive!): trait, public/private, generics, ...

Before starting, make sure you read the [introduction](https://github.com/astrolabsoftware/scala-tutorials/blob/master/README.md), especially how to set up the docker image if you do not have Scala installed on your machine.

## Mutable & immutable variables

An immutable object is an unmodifiable and read-only object, whose value cannot be changed once assigned, while a mutable object can be changed. In Scala, you must make the distinction when declaring a variable or an object. An immutable variable will be declared with the keyword `val`, and a mutable variable will be declared with the keyword `var`:

```scala
val a: Double = 1.0 // immutable
var b: Double = 1.0 // mutable
```

By default objects are immutable. You will see in the next exercise the implications.

## Type declaration

Scala has a strong static type system, but it does not require type declarations. For example

```scala
scala> val a: Int = 1 // Explicit
a: Int = 1

scala> val a = 1      // Inferred
a: Int = 1
```

When defining a function, only the decalaration of argument types is mandatory. The return type declaration is optional, though it must be unique (see next exercise)! For example both definitions below would work, and return `Int`:

```scala
// Specifying everything
def toto(a: Int): Int = {
  a + 1
}

// Not specifying the return type (Int inferred),
// and omitting the curly braces.
def toto(a: Int) = a + 1
```

## Class, object

### Class

Here is a simple class with one attribute, and one method.


```scala
/**
  * Some doc about `myClass`.
  */
class myClass {
  // A class attribute
  val attribute: Double = 1.0

  /**
    * Some doc for the method
    * @param arg input message
    * @return true if you love scala, otherwise false.
    */
  def aMethod(arg: String): Boolean = {
    // Pattern matching
    arg match {
      case "I love Scala" => true
      case _ => false
    }
  }
}
```

You can test it on a Scala shell:

```scala
scala> val mcl = new myClass // Instance
mcl: myClass = myClass@32e7b78d

scala> mcl.attribute
res0: Double = 1.0

scala> mcl.aMethod("Scala is boring")
res1: Boolean = false

scala> mcl.aMethod("I love Scala")
res2: Boolean = true
```

### Object

Scala has no static variables or methods, it has singleton objects. They are essentially classes with only one object in the class (mathematically: a set with one element; programmatically: a single instantiation of a class). They are traditionally used to define companion object to class or main program in a project.

#### Companion object

By convention, one places static variables and methods in a singleton object with the same name as the class name, which is then known as a companion object.

```scala
/**
  * Class person with name and age. Note that using
  * `val` in the constructor registers args as attributes
  * of the class directly.
  * @param name Name of the person (attribute)
  * @param age Age of the person (attribute)
  */
class Person(val name: String, val age: Int) {
  // Import attributes and methods from the companion object
  import Person._

  /** Empty constructor */
  def this() = this("", 0)

  /**
    * Compare the age of two Person instances.
    * @param p Instance of Person
    * @return true if you are older than p, false otherwise.
    */
  def isOlderThan(p: Person): Boolean = {
    if (this.age > p.age) {
      true
    } else false
  }

  /** Present yourself */
  def greet: Unit = sayHi(this.name)
}

/**
  * Companion object to the class `Person`.
  */
object Person {

  /** Present yourself */
  def sayHi(name: String): Unit = println(s"Hi! My name is $name")
}
```

#### Main

The simplest way in Scala to define a main, is to extend `App`:

```scala
/** Entry point for our application */
object HelloWorld extends App {
  println("Hello, World!")
}
```

Alternatively, you can define the `main` function:

```scala
object HelloWorld {
  /**
    * Entry point for our Scala application.
    * @param args List of arguments (String) to pass to the application.
    */
  def main(args : Array[String]): Unit = {
    println("Hello, World!")
  }
}
```

## Object-oriented extensions

Scala is a pure object-oriented language in the sense that every value is an object. Data types and behaviors of objects are described by classes and traits. Class abstractions are extended by subclassing. Example:

```scala
/**
  * Abstract containing common methods to
  * all subclasses.
  */
abstract class Cars {
  /** Return the color of the car */
  def color: String
}

class Ferrari extends Cars {
  def color: String = "red"
}

class McLaren extends Cars {
  def color: String = "blue"
}
```

## Documentation and comments

Scala documentation typically follows Java conventions:

```scala
/**
  * This is where the doc should be written.
  * @param myparam the description of `myparam` comes here
  * @return the description of the output comes here
  */
def toto(myparam: Int): Int = {
  // I am a comment
  a + 1
}
```
