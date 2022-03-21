package macros

import scala.reflect.macros.blackbox.Context

class MyMacros(val c: Context) {
  import c.universe._
  def anyVal[T](implicit t: WeakTypeTag[T]): Tree =
    withAnyValParam(t.tpe) { param =>
      q"""
        _root_.scala.Predef.implicitly[A[${param.typeSignature}]]
     """
    }.getOrElse(fail("A", t.tpe))

  private def fail(enc: String, t: Type) = {
    c.abort(
      c.enclosingPosition,
      s"could not find the implicit $enc for AnyVal Type $t"
    )
  }

  private def withAnyValParam[R](tpe: Type)(f: Symbol => R): Option[R] = {
    tpe.baseType(c.symbolOf[AnyVal]) match {
      case NoType => None
      case _ =>
        primaryConstructor(tpe).map(_.paramLists.flatten).collect {
          case param :: Nil => f(param)
        }
    }
  }

  private def primaryConstructor(t: Type) = {
    t.members.collectFirst {
      case m: MethodSymbol if m.isPrimaryConstructor =>
        m.typeSignature.asSeenFrom(t, t.typeSymbol)
    }
  }
}
