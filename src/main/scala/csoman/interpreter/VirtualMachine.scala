package csoman.interpreter

import scala.collection.Map
import scala.collection.mutable
import scala.collection.mutable.ListBuffer

class VirtualMachineError extends Throwable

case class VirtualMachine(var callStack: ListBuffer[Frame], // call stack of frames
                     var frame: Option[Frame],
                     var returnValue: Any,
                     var lastException: Throwable) {

  def makeFrame(code: String,
                globalNames: Map[String, Any],
                localNames: Map[String, Any],
                callargs: Map[String, Any] = Map[String, Any]()): Frame = {

    val currentFrame = frame.get

    var local_names = Map[String, Any]()
    val global_names = if (callStack.isEmpty) {
      local_names = local_names ++ globalNames
      Map("name" -> "main")
    }
    else {
      currentFrame.globalNames
    }
    local_names = local_names ++ callargs
    val newFrame = new Frame(code, global_names, local_names, currentFrame)
    newFrame
  }

  def runFrame(frame: Frame): Any = ???

  def pushFrame(frame: Frame): Unit = frame +: callStack

  def popFrame: Option[Frame] = {
    callStack.remove(0)
    frame = callStack.headOption
    frame
  }

  def runCode(code: String, globalNames: Map[String, Any], localNames: Map[String, Any]): Any = {
    """ An entry point to execute code using the virtual machine."""

    val frame = makeFrame(code, globalNames, localNames)

    val value = runFrame(frame)

    value
  }

  /**
   * Data stack manipulation
   *
   */
  def top(): Any = frame.get.dataStack.head

  def pop(): Any = frame.get.dataStack.remove(0)

  def push(values: ListBuffer[Any]):ListBuffer[Any] = frame.get.dataStack ++ values

  //  Pop a number of values from the value stack.
  //  A list of `n` values is returned, the deepest value first.
  def popn(n: Int):ListBuffer[Any] = {
    var result = ListBuffer[Any]()
    result = if (n != 0) {
      val res = frame.get.dataStack.take(n)
      frame.get.dataStack.remove(n)
      res
    } else ListBuffer[Any]()
    result
  }


  // dispatch method giant switch statement of Cython
  // in Byterun it dispatches by byte name to corresponding methods
  def dispatch() = ???




}