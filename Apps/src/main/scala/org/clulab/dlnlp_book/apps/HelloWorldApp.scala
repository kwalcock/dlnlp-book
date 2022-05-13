package org.clulab.dlnlp_book.apps

import me.shadaj.scalapy.py
import me.shadaj.scalapy.py.SeqConverters

object HelloWorldApp extends App {
  val listLengthPython = py.Dynamic.global.len(List(1, 2, 3).toPythonProxy)
  val listLength = listLengthPython.as[Int]

  println(listLength)
}
