import macros.MyMacros
import scala.language.experimental.macros

trait A[T]

object A {
  implicit def aString: A[String] = new A[String] {}
  
  // Compilable if used from Scala 2 or Scala 3 without -Xignore-scala2-macros, 
  //    but does not compile if used from Scala 3 with -Xignore-scala2-macros
  implicit def another[T <: AnyVal]: A[T] = macro MyMacros.nothing[T]

  // Compilable if used from Scala 3 without -Xignore-scala2-macros, 
  //    but does not compile if used from Scala 2 or Scala 3 with -Xignore-scala2-macros
  //
  // implicit def another: A[String] = macro MyMacros.nothing[String]

  // Compilable in all combinations
  //
  // implicit def another[T]: A[T] = macro MyMacros.nothing[T]
}
