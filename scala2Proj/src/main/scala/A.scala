import macros.MyMacros
import scala.language.experimental.macros

trait A[T]

object A {
  implicit def aString: A[String] = new A[String] {}
  // If removing `<: AnyVal`, it compiles even with -Xignore-scala2-macros
  implicit def aAnyVal[T <: AnyVal]: A[T] = macro MyMacros.nothing[T]
}
