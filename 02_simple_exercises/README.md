# Scala by the example

## Before starting

### Setting up the environment

Make sure you read the [introduction](https://github.com/astrolabsoftware/scala-tutorials/blob/master/README.md), especially how to set up the docker image if you do not have Scala installed on your machine, and launch a Scala shell.

### Pasting codes in the Scala shell

You can easily paste snippets of code in the shell using `:paste`

```scala
scala> :paste
// Entering paste mode (ctrl-D to finish)
...
// Exiting paste mode, now interpreting.
scala>
```

This can be useful for the exercises.

## Some functional programing concepts

### Strict vs non-strict (lazy) evaluation

Let's take a simple example (taken from M. Odersky):

```scala
// Initialise a Range
val data = (0 to 9)

// xs is f(data)
val xs = data.map(f)

// Action 1: filter some elements of xs and sum them
xs.filter(cond).sum

// Action 2: Take n elements of xs, and cast them as String
xs.take(n).toString
```

In the case of a strict language, `map` is evaluated once and we produce intermediate list `xs`. In the case of a lazy language, `map` is evaluated twice and there is no intermediate list.

In other words strict evaluates expressions as soon as they are available, rather than as needed. Lazy waits for the whole chain of actions before taking a decision on when the evaluation should happen.

Scala collections are **strict** by default. You can declare a variable lazy with the `lazy` keyword however. Note that Spark collections however are **lazy** by default.

### Transformation vs action

Let's take the example above. It is worth noting that in both cases (strict or lazy), the computation (from the point of view of I/O, or CPU computation) is triggered only in the case of an _action_ call such as `sum` or `take`.

`map`, or `filter` are called _transformations_, that is they transform change the representation of the data but without materialising it. For example, this allows the compiler to optimise the chain of transformations before executing the action (re-organising the order of transformations before executing for example).

### Immutability

An immutable object is an unmodifiable and read-only object, whose value cannot be changed once assigned, while a mutable object can be changed. In Scala, you must make the distinction when declaring a variable or an object. An immutable variable will be declared with the keyword `val`, and a mutable variable will be declared with the keyword `var`:

```scala
val a: Double = 1.0 // immutable
var b: Double = 1.0 // mutable
```

You cannot reassign a value to an immutable variable:

```scala
scala> val a: Double = 1.0
a: Double = 1.0

scala> a = 3.0
<console>:12: error: reassignment to val
       a = 3.0
         ^
```

Similarly, all of the collection objects (container types) in Scala, e.g. linked lists, arrays, sets and hash tables, are available in mutable and immutable variants, with the immutable variant considered the default implementation.

```scala
// Construct an immutable list
import scala.collection.immutable.List

// Once constructed, you cannot add elements
val L: List[Double] = 1.0::2.0::3.0::Nil
```

And the mutable version

```scala
import scala.collection.mutable.ListBuffer

// Note that it does not know yet the size of it
var L: ListBuffer[Double] = ListBuffer[Double]()

// Fill it
for (i <- 1 to 3) {
  L += i.toDouble
}

// Oooops I changed my mind
L(0) = 2.9

// Materialise it explicitly if needed
L.result()
```

Among several, the advantages of using immutable objects are:

- Very easy concurrency — no locks are needed as no shared objects are ever modified.
- Efficient object construction - modified instance reuses most of old instance data and unused/unreferenced parts are collected by the garbage collector.
- Less stupid bugs, as once declared the value of a variable cannot change (note that the type cannot change as well).

### Higher-order functions

A higher-order function is a function which (1) takes one or more functions as inputs, and (2) returns a function:

```scala
// From wiki - apply twice the input function
def twice(f:Int => Int) = f compose f

// Long version
def addThree(v: Int): Int = v + 3
twice(addThree)(6)
//res0: Int = 12

// Shorter version
twice(x => x + 3)(6)

// Shortest version
twice(_ + 3)(6)
```

Note that `_` refers as to the input argument to the inner function (here `6` and then `6 + 3`).

### (Tail) Recursion

Scala (as most of functional programming languages) provides tail call optimization to allow for extensive use of recursion without typical stack overflow problems. Example:

```scala
/**
  * Recursively build a list of random integers
  * @param myList Empty list to fill
  * @param count Internal counter for the recursion, optional
  * @param stop Size of the list, optional
  */
def buildRandomList(myList: List[Int], count: Int=0, stop: Int=10): List[Int] = {
  if (count == stop) {
    Nil
  } else {
    val r = scala.util.Random
    r.nextInt :: buildRandomList(myList, count + 1, stop)
  }
}

val L = List[Int]() // Initialise empty list
buildRandomList(L)
```

You can also add the `@tailrec` decorator before the function declaration, in which case it will not compile unless it is tail recursive (good for debugging!). Note that the list is not materialised until the whole loop is known and performed.

### Referential transparency

The definition of referential transparency is: _An expression is called referentially transparent if it can be replaced with its corresponding value without changing the program's behavior._ In practice, that means the evaluation of a function must be the same for the same inputs, and its evaluation must have no side effects (i.e. changing the value of objects defined outside its scope). For example, the function `buildRandomList ` defined above is not referentially transparent as it returns different random numbers for different evaluations.

Below is an example of a referentially transparent function:

```scala
/**
  * Recursively build a list of ordered integers
  * @param myList Empty list to fill
  * @param count Internal counter for the recursion, optional
  * @param stop Size of the list, optional
  */
def buildIncreasingList(myList: List[Int], count: Int=0, stop: Int=10): List[Int] = {
  if (count == stop) {
    Nil
  } else {
    count :: buildIncreasingList(myList, count + 1, stop)
  }
}

val L = List[Int]() // Initialise empty list
buildIncreasingList(L, 0, 10)
// always List(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)
```

### Return or not return?

Should we use `return` at the end of a function in Scala? Pro tip:

```
Don't use return in Scala.
```

A very detail explanation on why can be found [here](https://tpolecat.github.io/2014/05/09/return.html). Here is a simple example taken from this blog highlighting the perversity of `return`:

```scala
// No return is used
def sum(ns: Int*): Int = ns.foldLeft(0)((n, m) => n + m)

scala> sum(33, 42, 99)
res2: Int = 174 // alright

// Use return explicitly
def sumR(ns: Int*): Int = ns.foldLeft(0)((n, m) => return n + m)

scala> sumR(33, 42, 99)
res3: Int = 33 // Ooops.
```

Try to find why there is a difference! (answer [here](https://tpolecat.github.io/2014/05/09/return.html)).

### Pimp my library!

Scala implements the idea of [extension method](https://en.wikipedia.org/wiki/Extension_method), or non-officially called "pimp my library". The idea is to define implicit methods to add on existing objects. Example:

```scala
object MyExtensions {
  /**
    * Class containing implicit methods
    */
  implicit class Containers(s: String) {
    /**
      * Implicit method which tests whether a String contains the letter "e".
      * @return Boolean. true is "e" is in s, false otherwise.
      */
    def containsE: Boolean = s.contains("e")
  }
}

// bring implicit enrichment into scope
import MyExtensions._  
val a = "Hello, I'm Julien!"
val b = "Wait!"
a.containsE  // -> true
b.containsE  // -> false
```
