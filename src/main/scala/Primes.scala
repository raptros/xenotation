package xeno

import math._

import scala.util.parsing.combinator._

import scalaz._
import annotation.tailrec

import Stream.{Empty => SNil}

object Primes {
  import scala.collection.mutable.ListBuffer
  type BStr = Stream[BigInt]
  type PBuf = ListBuffer[BigInt]

  val primes:BStr = (BigInt(2) #:: sieve(BigInt(3), ListBuffer(BigInt(3))))

  /** gets the nth prime (i.e. primes(n-1) )*/
  def apply(n:BigInt):BigInt = if (n.isValidInt) primes(n.intValue()) else sys.error("too far!")

  /** returns the index of the current prime */
  def unapply(n:BigInt):Option[BigInt] = {
    val within = primes takeWhile { _ <= n }
    (within.zipWithIndex) find { p => p._1 == n } map { p => BigInt(p._2) }
  }

  def sieve(p:BigInt, ps:PBuf):BStr = { 
    val np = nextPrime(p, ps)
    p #:: sieve(np, ps += np)
  }

  def nextPrime(p:BigInt, ps:PBuf) = iterFrom(p) find {
    i => ps takeWhile { j => (j pow 2) <= i  } forall {  e => (i % e) != 0 }
  } getOrElse sys.error("something went wrong getting next prime!")

  def iterFrom(p:BigInt):BStr = Stream.iterate(p + BigInt(2)) { _ + BigInt(2) }

  @tailrec
  def getPow(n:BigInt, prime:BigInt, pt:BigInt=BigInt(0)):BigInt = {
    val (q, r) = n /% prime
    if (q >= 0 && r == 0) getPow(q, prime, pt + BigInt(1)) else pt
  }

  def factors(n:BigInt):Stream[(BigInt, BigInt)] = for {
    prime <- primes takeWhile { _ <= n } 
    (q, r) = n /% prime if (r == 0)
  } yield prime -> (getPow(q, prime, BigInt(1)))
}

object XenoPrimes {
  /** converts the primes stream into a stream of Xeno objects.
    * @note it's xrep(i+1) because the notion of braketing is indexed from 1.
    */
  val xprimes:Stream[Xeno] = S0 #:: Brak(S0) #:: (Primes.primes.tail.tail map {
    case Primes(i) => Brak(xrep(i + 1)).asInstanceOf[Xeno]
  })

  def apply(n:BigInt):Xeno = if (n.isValidInt) xprimes(n.intValue()) else sys.error("too far!")

  def xrep(n:BigInt):Xeno = xfactors(n) match {
    case SNil => sys.error(s"apparently ${n} has no factors")
    case x #:: SNil => x
    case x #:: xs => (x #:: xs) reduce { _ * _ }
    case _ => sys.error("no idea")
  }

  def xfactors(n:BigInt):Stream[Xeno] = Primes.factors(n) map {
    case (Primes(idx), exponent) => apply(idx) pow exponent.toInt
  }
}
