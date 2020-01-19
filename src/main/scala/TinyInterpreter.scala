import common.FileHelper

import scala.collection.mutable.{ListBuffer, Map}
import sys.process._
import scala.language.postfixOps

// later pass python file to the class as well
object Compiler extends App {

  // running a shell script from scala
  try {
    "scripts/generateAssembly.sh" !!
  }
  catch {
    case t: Throwable => println("Exception while converting python code to assembly code: "+ t)
  }

  val assemblyFilePath = "intermediateFiles/assembly/"
  val assemblyFileName = "test.txt"

  var IndexToInstruction = Map[Int, String]()
  var instructions = new ListBuffer[(String, Any)]()
  var variableValues = new ListBuffer[String]() // numbers
  var variableNames = new ListBuffer[String]() // names

  val newLineGOTO = """^\s*([0-9]+)\s+([>]+)\s+([0-9]+)\s+([A-Z_]+)\s+([0-9]+)?\s*([-A-Za-z0-9_, '!@#$%^&*()<>{}.+=]+)?\s*$""".r
  val notANewLineGOTO = """^\s*([>]+)\s+([0-9]+)\s+([A-Z_]+)\s+([0-9]+)?\s*([-A-Za-z0-9_, !@#$%^&'*()<>{}.+=]+)?\s*$""".r
  val newLine = """^\s*([0-9]+)\s+([0-9]+)\s+([A-Z_]+)\s+([0-9]+)?\s*([-A-Za-z0-9_, !@#$%^&*()<>{'}.+=]+)?\s*$""".r
  val notANewLine = """^\s+([0-9]+)\s+([A-Z_]+)\s+([0-9]+)?\s*([-A-Za-z0-9_, !@#$%^&*()<>{}.+'=]+)?\s*$""".r
  val onlyInstruction = """^\s*([0-9]+)\s+([A-Z_]+)\s*$""".r
  val onlyInstructionGOTO = """^\s*([>]+)\s+([0-9]+)\s+([A-Z_]+)\s*$""".r

  // limitation on variable name
  val variableNamesConstraint = """^([(]{1})([-A-Za-z_]+)([)]{1})$""".r // otherwise illegal variable name

  val notVariableNames = Set("False",    "class",    "finally",    "is",    "return",    "None",
    "for",    "lambda",    "try",    "True",    "def	",    "from",    "nonlocal",    "while",
    "and",    "del",    "global",    "not",    "with",    "as",    "elif",    "if",  "continue",
    "or",    "yield",    "assert",    "else",    "import",    "pass",    "break",    "except",
    "in",    "raise")

  // It is time to assign type to dynamically typed language!
  // Considering only Boolean, Int, String and Float types!!
  val stringValueRegex = """^([(]{1}[']{1})([-A-Za-z0-9_, !@#$%^&*()<>{}.+=]*)([']{1}[)]{1})$""".r
  val variableValueRegex = """^([(]{1})([-0-9.]*)([)]{1})$""".r

  // --> Cannot use Regex to distinguish between variable names and values! Need to have something else.
  // Let's distinguish them on the basis on the instruction in hand.


  // fetch the assembly file and transform it such that our interpreter would be able to use it.
  FileHelper.using(io.Source.fromFile(assemblyFilePath + assemblyFileName)) {
    source =>
      source.getLines().foreach { line =>
        println(line)
        line match {
          case newLineGOTO(lineNumber, goto, instructionIndex, instruction, index, value) =>
            println(lineNumber, goto, instructionIndex, instruction, index, value)
            //println(instruction,value)
            instructions += instruction -> index.toInt

            value match {
              case variableNamesConstraint(_,variableName,_) =>
                if (!notVariableNames.contains(variableName)) variableNames += variableName
                else ()
              case stringValueRegex(_,variableValue,_) => variableValues += variableValue
              case variableValueRegex(_,variableValue,_) => variableValues += variableValue
              case _ => println(s"didn't match: ${value}")
            }

          case notANewLineGOTO(goto, instructionIndex, instruction, index, value) =>
            println(goto, instructionIndex, instruction, index, value)
            //println(instruction,value)
            instructions += ((instruction, index.toInt))

            value match {
              case variableNamesConstraint(_,variableName,_) =>
                if (!notVariableNames.contains(variableName)) variableNames += variableName
                else ()
              case stringValueRegex(_,variableValue,_) => variableValues += variableValue
              case variableValueRegex(_,variableValue,_) => variableValues += variableValue
              case _ => println(s"didn't match: ${value}")
            }

          case newLine(lineNumber, instructionIndex, instruction, index, value) =>
            println(lineNumber, instructionIndex, instruction, index, value)
            //println(instruction,value)
            instructions += instruction -> index.toInt

            value match {
              case variableNamesConstraint(_,variableName,_) =>
                if (!notVariableNames.contains(variableName)) variableNames += variableName
                else ()
              case stringValueRegex(_,variableValue,_) => variableValues += variableValue
              case variableValueRegex(_,variableValue,_) => variableValues += variableValue
              case _ => println(s"didn't match: ${value}")
            }

          case notANewLine(instructionIndex, instruction, index, value) =>
            println(instructionIndex, instruction, index, value)
            //println(instruction,value)
            instructions += ((instruction, index.toInt))

            value match {
              case variableNamesConstraint(_,variableName,_) =>
                if (!notVariableNames.contains(variableName)) variableNames += variableName
                else ()
              case stringValueRegex(_,variableValue,_) => variableValues += variableValue
              case variableValueRegex(_,variableValue,_) => variableValues += variableValue
              case _ => println(s"didn't match: ${value}")
            }

          case onlyInstructionGOTO(goto, instructionIndex, instruction) =>
            println(goto, instructionIndex, instruction)
            instructions += ((instruction, "None"))

          case onlyInstruction(instructionIndex, instruction) =>
            println(instructionIndex, instruction)
            instructions += ((instruction, "None"))

          case _ => ()//println("Nothing")
        }
      }
  }
//  println("--------------")
//  variableValues.foreach(println)
//  println("--------------")
//  variableNames.foreach(println)
//  instructions.foreach(println)
}
//  def LOAD_VALUE(number: Int) = {
//    stack += number
//    println(s"loaded value $number")
//  }
//
//  def PRINT_ANSWER() = {
//    val answer = stack.head
//    println(answer)
//  }
//
//  def ADD_TWO_VALUES() = {
//    val first_num = stack.head
//    val second_num = stack.head
//    val total = first_num + second_num
//    stack += total
//    println(s"added $first_num and $second_num")
//  }
//}
//
//
//object test extends App {
//  var what_to_execute = Map[String, List[T]]()
//  what_to_execute += "instructions" -> List(("LOAD_VALUE", 0), // the first number
//    ("LOAD_VALUE", 1),  // the second number
//    ("ADD_TWO_VALUES", None),
//    ("PRINT_ANSWER", None))
//  "numbers" -> List(7, 5)
//}
//
///*
//
//class TinyInterpreter(var dataStack: ListBuffer[Int]):
//
//    def LOAD_VALUE(self, number):
//        self.stack.append(number)
//
//    def PRINT_ANSWER(self):
//        answer = self.stack.pop()
//        print(answer)
//
//    def ADD_TWO_VALUES(self):
//        first_num = self.stack.pop()
//        second_num = self.stack.pop()
//        total = first_num + second_num
//        self.stack.append(total)
//
//    def run_code(self, what_to_execute):
//        instructions = what_to_execute["instructions"]
//        numbers = what_to_execute["numbers"]
//        for each_step in instructions:
//            instruction, argument = each_step
//            if instruction == "LOAD_VALUE":
//                number = numbers[argument]
//                self.LOAD_VALUE(number)
//            elif instruction == "ADD_TWO_VALUES":
//                self.ADD_TWO_VALUES()
//            elif instruction == "PRINT_ANSWER":
//                self.PRINT_ANSWER()
//
//    interpreter = Interpreter()
//    interpreter.run_code(what_to_execute)
//
//    -----------------
//
//>>> def s():
//...     a = 1
//...     b = 2
//...     print(a + b)
//# a friendly compiler transforms `s` into:
//    what_to_execute = {
//        "instructions": [("LOAD_VALUE", 0),
//                         ("STORE_NAME", 0),
//                         ("LOAD_VALUE", 1),
//                         ("STORE_NAME", 1),
//                         ("LOAD_NAME", 0),
//                         ("LOAD_NAME", 1),
//                         ("ADD_TWO_VALUES", None),
//                         ("PRINT_ANSWER", None)],
//        "numbers": [1, 2],
//        "names":   ["a", "b"] }
//
//
//
//class Interpreter:
//    def __init__(self):
//        self.stack = []
//        self.environment = {}
//
//    def STORE_NAME(self, name):
//        val = self.stack.pop()
//        self.environment[name] = val
//
//    def LOAD_NAME(self, name):
//        val = self.environment[name]
//        self.stack.append(val)
//
//    def parse_argument(self, instruction, argument, what_to_execute):
//        """ Understand what the argument to each instruction means."""
//        numbers = ["LOAD_VALUE"]
//        names = ["LOAD_NAME", "STORE_NAME"]
//
//        if instruction in numbers:
//            argument = what_to_execute["numbers"][argument]
//        elif instruction in names:
//            argument = what_to_execute["names"][argument]
//
//        return argument
//
//    def run_code(self, what_to_execute):
//        instructions = what_to_execute["instructions"]
//        for each_step in instructions:
//            instruction, argument = each_step
//            argument = self.parse_argument(instruction, argument, what_to_execute)
//
//            if instruction == "LOAD_VALUE":
//                self.LOAD_VALUE(argument)
//            elif instruction == "ADD_TWO_VALUES":
//                self.ADD_TWO_VALUES()
//            elif instruction == "PRINT_ANSWER":
//                self.PRINT_ANSWER()
//            elif instruction == "STORE_NAME":
//                self.STORE_NAME(argument)
//            elif instruction == "LOAD_NAME":
//                self.LOAD_NAME(argument
//----
//     def execute(self, what_to_execute):
//        instructions = what_to_execute["instructions"]
//        for each_step in instructions:
//            instruction, argument = each_step
//            argument = self.parse_argument(instruction, argument, what_to_execute)
//            bytecode_method = getattr(self, instruction)
//            if argument is None:
//                bytecode_method()
//            else:
//                bytecode_method(argument)
//
// */