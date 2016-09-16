package reductions

import java.util.concurrent._
import scala.collection._
import org.scalatest.FunSuite
import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner
import common._
import java.util.concurrent.ForkJoinPool.ForkJoinWorkerThreadFactory

@RunWith(classOf[JUnitRunner]) 
class LineOfSightSuite extends FunSuite {
  import LineOfSight._
  test("lineOfSight should correctly handle an array of size 4") {
    val output = new Array[Float](4)
    lineOfSight(Array[Float](0f, 1f, 8f, 9f), output)
    assert(output.toList == List(0f, 1f, 4f, 4f))
  }


  test("upsweepSequential should correctly handle the chunk 1 until 4 of an array of 4 elements") {
    val res = upsweepSequential(Array[Float](0f, 1f, 8f, 9f), 1, 4)
    assert(res == 4f)
  }


  test("downsweepSequential should correctly handle a 4 element array when the starting angle is zero") {
    val output = new Array[Float](4)
    downsweepSequential(Array[Float](0f, 1f, 8f, 9f), output, 0f, 1, 4)
    assert(output.toList == List(0f, 1f, 4f, 4f))
  }

  test("upsweep") {
    val res = upsweep(Array[Float](0.0f, 7.0f, 10.0f, 33.0f, 48.0f), 1, 5, 2)
    assert(res == Node(Leaf(1,3,7.0f),Leaf(3,5,12.0f)))
  }
  test("downsweep") {
    val input = Array[Float](0.0f, 7.0f, 10.0f, 33.0f, 48.0f)
    val res = new Array[Float](5)
    downsweep(input, res, 0f, Node(Leaf(1,3,7.0f),Leaf(3,5,12.0f)))
    assert(res.toList == List(0.0f, 7.0f, 7.0f, 11.0f, 12.0f))
  }

  test("parLineOfSigh") {
    val input = Array[Float](0.0f, 7.0f, 10.0f, 33.0f, 48.0f)
    val output = new Array[Float](5)
    parLineOfSight(input, output, 2)

    assert(output.toList == List(0.0, 7.0, 7.0, 11.0, 12.0))
  }

}

