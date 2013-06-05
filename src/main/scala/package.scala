package object xeno {
  implicit class StringXeno(str:String) {
    def x:Xeno = Xeno(str)
    def xe:BigInt = x.eval
  }
}
