package xeno

object XenoReader extends App {
  val in = args(0)
  println(s"parsing ${in}")
  val x = XenoParse.p(in)
  println(s"successfully parsed $x")
  println(s"evaluated: ${x.eval}")
}

object XenoConvert extends App {
  println(Xeno(BigInt(args(0))))
}
