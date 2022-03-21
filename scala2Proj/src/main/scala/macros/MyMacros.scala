package macros

import scala.reflect.macros.blackbox.Context

class MyMacros(val c: Context) {
  import c.universe._
  def nothing[T](implicit t: WeakTypeTag[T]): Tree =
    q"""
        ???
     """
}
