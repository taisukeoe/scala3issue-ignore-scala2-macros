# What is this project?

This project reproduce an issue about `-Xignore-scala2-macros` scala3 compiler option.

# What issue?

Giving following implicits, one is a plain implicit and another is implicit macro,

```scala
// Scala 2 source codes with a macro
trait A[T]
object A {
  implicit def aString: A[String] = new A[String] {}
  implicit def another[T <: AnyVal]: A[T] = macro MyMacros.nothing[T]
}
```

Following Scala 3 code compiles without `-Xignore-scala2-macros`, but does NOT compile with `-Xignore-scala2-macros`.

```scala
// Scala 3 code which use above `object A`
object SummonA {
  val aString = implicitly[A[String]]
}
```

Compilation error with `-Xignore-scala2-macros`.

```
sbt:scala3Proj> compile
[info] compiling 1 Scala source to /path/to/IgnoreScala2Macro/scala2Proj/target/scala-2.13/classes ...
[info] compiling 1 Scala source to /path/to/IgnoreScala2Macro/scala3Proj/target/scala-3.1.1/classes ...
[error] -- Error: /path/to/IgnoreScala2Macro/scala3Proj/src/main/scala/SummonA.scala:2:37 
[error] 2 |  val aString = implicitly[A[String]]
[error]   |                                     ^
[error]   |ambiguous implicit arguments: both method aString in object A and method another in object A match type A[String] of parameter e of method implicitly in object Predef
```

# Real world example

These `aString` and `another` implicits are minimized versions of [QueryStringBindable.bindableString](https://github.com/playframework/playframework/blob/78bcdb4e1d1fe0a350ab2a56a38649571415383f/core/play/src/main/scala/play/api/mvc/Binders.scala#L336) and [QueryStringBindable.anyValQueryStringBindable](https://github.com/playframework/playframework/blob/78bcdb4e1d1fe0a350ab2a56a38649571415383f/core/play/src/main/scala/play/api/mvc/Binders.scala#L649-L650) in Play Framework.

With Play Framework, generated `Routes.scala` is showing the same error as reported above.