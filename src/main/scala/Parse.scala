package xeno

import scala.util.parsing.combinator._

object XenoParse extends JavaTokenParsers {
  def xeno:Parser[Xeno] = seqk | brak | s0
  def s0 = "0" ^^^ { S0 }
  def brak = "(" ~> xeno <~ ")" ^^ {
    x => Brak(x)
  }
  def seqk = (brak | s0) ~ rep1(brak | s0) ^^ {
    case x ~ xs => Seqk((x::xs):_*)
  }

  def p(x:String) = parseAll(xeno, x) match {
    case Success(x, _) => x
    case Error(msg, remaining) => sys.error(s"parsing error: $msg, ${remaining.toString}")
    case Failure(msg, remaining) => sys.error(s"parsing failure: $msg, ${remaining.toString}")
  }
}


