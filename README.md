# Functional programming & Scala

## Demystifying functional programming and Scala.

### Functional programming

Functional programming is a paradigm coming from the declarative programming.
Despite what we can sometimes hear, the idea of functional programming is not new. In the late 1950s, [Lisp](https://en.wikipedia.org/wiki/Lisp_(programming_language)) paved the road for the yet to become large family of functional programming languages. It recently became popular again with the rise of big data and machine learning.

### Scala: yet another Java cousin?

[Scala](https://scala-lang.org/) is a general-purpose programming language that has been started in 2004 by Martin Odersky (EPFL).
The language is inter-operable with Java and Java-like languages, and Scala executables run on the Java Virtual Machine (JVM). Like Java it has a strong static type system, but it does not require type declarations.
Finally, Scala being younger than Java (1995), its design tries to solve some of the problems of Java.

### Scala: _scalable language_

Scala is a portmanteau of scalable and language, to reflect the fact that the language is designed to grow and easily integrate new features over time. There are pros and cons to this... For example it is easy to extend or add specific features from other languages, but then there is no guarantee for backward compatibility between Scala versions. In addition, the syntax of the language is not unique (many _optional_ rules, like type hints).

### Multi-paradigm

Scala is not a pure functional programming language. It is multi-paradigm, including functional programming, imperative programming, object-oriented programming and concurrent computing.

### What you will learn and will not learn here

- **Yes:** A few functional programming concepts through examples in Scala.

- **No:** be an expert in Scala.

## Working with Docker

We provide several images for the need of the exercise. We try to keep the size of the image as light as possible, and it is based on the alpine distribution (the image size is about 100 Mo). To play with the image, build it or download it:

### Building from the DockerFile

```bash
# Go to the folder containing the Dockerfile
cd docker/alpine/
# Build the image - <name> can be scala/2.11.8 for example
docker build -t <name> .
# List the images - scala/2.11.8 should be there!
docker image ls
```

### Download the image

For the JI, we recommend to the users to clone the repo on their machine, and to download the Scala image:

```bash
IMAGE=gitlab-registry.in2p3.fr/maitresnageurs/quatrenages/scala:2.11.8
docker run --rm --tty --interactive --volume $PWD:/app ${IMAGE} scala
```

### Unix users

For convenience, you can define aliases to use the docker as if you were on your local directory directly. For example, to launch a Scala REPL (scala shell if you prefer) from your machine:

```bash
toto@local~:$ imageID="gitlab-registry.in2p3.fr/maitresnageurs/quatrenages/scala:2.11.8"
toto@local~:$ alias myscala='docker run --rm --tty --interactive --volume $PWD:/app ${imageID} scala'
toto@local~:$ myscala
Welcome to Scala 2.11.8 (OpenJDK 64-Bit Server VM, Java 1.8.0_151).
Type in expressions for evaluation. Or try :help.

scala>
```

Obviously it runs from the docker, but you do not need to know it! Same trick can be use for the compiler

```bash
toto@local~:$ imageID="gitlab-registry.in2p3.fr/maitresnageurs/quatrenages/scala:2.11.8"
toto@local~:$ alias myscalac='docker run --rm --tty --interactive --volume $PWD:/app ${imageID} scalac'
toto@local~:$ myscalac whatever.scala # compile
toto@local~:$ myscala whatever.scala  # or compile/execute directly
```

Finally, it is always a good idea to define aliases to avoid typing long command. We put a bash script with a few pre-define aliases in `scripts/`. You can activate it by sourcing the script in there: `source init.sh`. We encourage you to add new aliases!

### Windows users

Sorry, I have no idea how all of that works on Windows!
If you tried, do not hesitate to report anything weird you encountered (or let me know if that works)!

## *Demandez le programme!*

We split the work in 3 parts (~40 min):

- [Syntax review](https://github.com/astrolabsoftware/scala-tutorials/tree/master/01_syntax): A very brief overview of the Scala syntax (15 min).
- [simple exercises](https://github.com/astrolabsoftware/scala-tutorials/tree/master/02_simple_exercises): simple exercises to discover a few functional programming concepts and Scala (20 min).
- [extensions](https://github.com/astrolabsoftware/scala-tutorials/tree/master/03_extensions): domain-specific languages (DSL) in Scala (5 min).
