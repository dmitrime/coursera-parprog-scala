package reductions

import scala.annotation._
import org.scalameter._
import common._

object ParallelParenthesesBalancingRunner {

  @volatile var seqResult = false

  @volatile var parResult = false

  val standardConfig = config(
    Key.exec.minWarmupRuns -> 40,
    Key.exec.maxWarmupRuns -> 80,
    Key.exec.benchRuns -> 120,
    Key.verbose -> true
  ) withWarmer(new Warmer.Default)

  def main(args: Array[String]): Unit = {
    val length = 100000000
    val chars = new Array[Char](length)
    val threshold = 10000
    val seqtime = standardConfig measure {
      seqResult = ParallelParenthesesBalancing.balance(chars)
    }
    println(s"sequential result = $seqResult")
    println(s"sequential balancing time: $seqtime ms")

    val fjtime = standardConfig measure {
      parResult = ParallelParenthesesBalancing.parBalance(chars, threshold)
    }
    println(s"parallel result = $parResult")
    println(s"parallel balancing time: $fjtime ms")
    println(s"speedup: ${seqtime / fjtime}")
  }
}

object ParallelParenthesesBalancing {

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def balance(chars: Array[Char]): Boolean = {
    var open = 0
    for (c <- chars) {
      if (c == '(')
        open += 1
      else if (c == ')') {
        if (open == 0)
          return false
        open -= 1
      }
    }
    return open == 0
  }

  /** Returns `true` iff the parentheses in the input `chars` are balanced.
   */
  def parBalance(chars: Array[Char], threshold: Int): Boolean = {

    //def traverse(idx: Int, until: Int, arg1: Int, arg2: Int): (Int,Int) = {
      //var close = arg2
      //var open = arg1

      //var i = idx
      //while (i < until) {
        //if (chars(i) == '(')
          //open += 1
        //else if (chars(i) == ')' && open > 0) {
          //open -= 1
        //}
        //i += 1
      //}

      //var i = until - 1
      //while (i >= idx) {
        //if (chars(i) == ')')
          //close += 1
        //else if (chars(i) == '(' && close > 0) {
          //close -= 1
        //}
        //i -= 1
      //}

      //(open, close)
    //}

    //def reduce(from: Int, until: Int, arg1: Int, arg2: Int): (Int,Int) = {
      //if (until - from <= threshold)
        //traverse(from, until, arg1, arg2)
      //else {
        //val mid = from + (until - from) / 2
        //val (a, b) = parallel(reduce(from, mid, arg1, 0), reduce(mid, until, 0, arg2))

        //(a._1 + b._1, a._2 + b._2)
      //}
    //}

    //reduce(0, chars.length, 0, 0) == (0, 0)
    false
  }

  // For those who want more:
  // Prove that your reduction operator is associative!

}
