package xeno

import math._
import scalaz._
import annotation.tailrec

sealed trait Xeno {
  def eval:BigInt
  def weight:Int
  def xrep:String
  override def toString = xrep
  def *(other:Xeno):Xeno = Seqk(this, other)
  def pow(count:Int):Xeno = Seqk(List.fill(count)(this):_*)
}

object Xeno {
  def apply(str:String):Xeno = XenoParse.p(str)
  def apply(n:BigInt):Xeno = XenoPrimes.xrep(n)
}

case object Ident extends Xeno {
  val eval = BigInt(1)
  val weight = 0
  val xrep = ""
}

case object S0 extends Xeno {
  val eval = BigInt(2)
  val weight = 1
  val xrep = "0"
}

case class Brak(x:Xeno) extends Xeno {
  lazy val eval = Primes(x.eval - 1)
  lazy val weight = x.weight + 1
  lazy val xrep = s"(${x.xrep})"
}

case class Seqk(xs:Xeno*) extends Xeno {
  val comps:List[Xeno] = { 
    val (seqks, others) = xs.toList partition { _.isInstanceOf[Seqk] }
    val subComps = seqks map { _.asInstanceOf[Seqk].comps }
    val sorted = (others::subComps).flatten sortBy { _.weight }
    sorted.reverse
  }

  def eval = (Ident::comps) map { _.eval } reduce { _ * _ }

  def weights = comps map { _.weight }

  def weight = weights.headOption map { wh =>
    wh * (weights count { _ == wh })
  } getOrElse 0
  
  def xrep = comps map { _.xrep } mkString ""
}
