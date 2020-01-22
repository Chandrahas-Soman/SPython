package csoman.interpreter

import scala.collection.Map
import scala.collection.mutable.ListBuffer

case class Frame(var codeObject: String, // created by the compiler
            var globalNames: Map[String, Any],
            var localNames: Map[String, Any],
            var prevFrame: Frame,
            var dataStack: ListBuffer[Any] = new ListBuffer[Any],
            //var builtinName: Set[String],
            var lastInstruction: Int = 0, // later create enum or case class for Instructions!
            var blockStack: ListBuffer[Any] = new ListBuffer[Any]) {

//  builtinName = if (prevFrame == Some) prevFrame.get.builtinName
//                else if (builtinName.contains("__dict__")) builtinName // builtinName.__dict__
//                else localName("builtin")

}
