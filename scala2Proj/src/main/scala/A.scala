import macros.MyMacros
import scala.language.experimental.macros

trait A[T]

object A {
  implicit def aString: A[String] = new A[String] {}
  implicit def aAnyVal[T <: AnyVal]: A[T] = macro MyMacros.anyVal[T]
}
