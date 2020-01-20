package csoman.interpreter

import scala.collection.mutable
import scala.collection.mutable.{ListBuffer, Map}
import sys.process._
import scala.language.postfixOps

class Preprocessor() {

  var instructions = new ListBuffer[(String, Any)]()
  var variableValues = new ListBuffer[String]() // numbers
  var variableNames = new ListBuffer[String]() // names
  var environment: mutable.Map[String, Any] = mutable.Map[String, Any]()

  //TODO improve REGEX!!
  val newLineGOTO = """^\s*([0-9]+)\s+([>]+)\s+([0-9]+)\s+([A-Z_]+)\s+([0-9]+)?\s*([-A-Za-z0-9_, '!@#$%^&*()<>{}.+=]+)?\s*$""".r
  val notANewLineGOTO = """^\s*([>]+)\s+([0-9]+)\s+([A-Z_]+)\s+([0-9]+)?\s*([-A-Za-z0-9_, !@#$%^&'*()<>{}.+=]+)?\s*$""".r
  val newLine = """^\s*([0-9]+)\s+([0-9]+)\s+([A-Z_]+)\s+([0-9]+)?\s*([-A-Za-z0-9_, !@#$%^&*()<>{'}.+=]+)?\s*$""".r
  val notANewLine = """^\s+([0-9]+)\s+([A-Z_]+)\s+([0-9]+)?\s*([-A-Za-z0-9_, !@#$%^&*()<>{}.+'=]+)?\s*$""".r
  val onlyInstruction = """^\s*([0-9]+)\s+([A-Z_]+)\s*$""".r
  val onlyInstructionGOTO = """^\s*([>]+)\s+([0-9]+)\s+([A-Z_]+)\s*$""".r

  // limitation on variable name
  val variableNamesConstraint = """^([(]{1})([-A-Za-z_]+)([)]{1})$""".r // otherwise illegal variable name

  // Variable names should NOT be from following set
  val notVariableNames = Set("False",    "class",    "finally",    "is",    "return",    "None",
    "for",    "lambda",    "try",    "True",    "def	",    "from",    "nonlocal",    "while",
    "and",    "del",    "global",    "not",    "with",    "as",    "elif",    "if",  "continue",
    "or",    "yield",    "assert",    "else",    "import",    "pass",    "break",    "except",
    "in",    "raise")

  // It is time to assign a type to dynamically typed language!
  // Considering only Boolean, Int, String and Float types!!
  val stringValueRegex = """^([(]{1}[']{1})([-A-Za-z0-9_, !@#$%^&*()<>{}.+=]*)([']{1}[)]{1})$""".r
  val variableValueRegex = """^([(]{1})([-0-9.]*)([)]{1})$""".r

  //TODO in future
  // --> Cannot use Regex to distinguish between variable names and values! Need to have something else.

  /**
   * Generate Assembly Code
   *
   */
  def createAssemblyCode(pythonFile: String): Option[List[String]] = {
    try {
      val result = Seq("python3", "-m", "dis", "python/tests/" + pythonFile).!!
      val assemblyCode:List[String] = result.split("\n").toList
      Some(assemblyCode)
    }
    catch {
      case t: Throwable => {
        println("Exception while converting python code to assembly code: " + t)
        None
      }
    }
  }

  /**
   * helper utility for assembly parser
   *
   */
  def populateNumbersAndNames(instruction: String, value: String) = {
    val numbers = Set("LOAD_CONST")
    val names = Set("LOAD_NAME", "STORE_NAME")

    value match {
      case variableNamesConstraint(_,variableName,_) =>
        if (!notVariableNames.contains(variableName) && names.contains(instruction)) variableNames += variableName
        else ()

      case stringValueRegex(_,variableValue,_) =>
        if(numbers.contains(instruction)) variableValues += variableValue
        else ()

      case variableValueRegex(_,variableValue,_) =>
        if(numbers.contains(instruction)) variableValues += variableValue
      else ()

      case _ => println(s"didn't match: ${value}")
    }

  }

  /**
   * Parse The Assembly Code
   *
   */
  def assemblyParser(assemblyFile: Option[List[String]]): mutable.Map[String, Any] = {

    val assemblyCode = assemblyFile.get

    // fetch the assembly file and transform it such that our interpreter would be able to use it.
    assemblyCode.foreach { line =>
      //println(line)
      line match {
        case newLineGOTO(lineNumber, goto, instructionIndex, instruction, index, value) =>
          //println(lineNumber, goto, instructionIndex, instruction, index, value)
          //println(instruction,value)
          instructions += instruction -> index.toInt

          populateNumbersAndNames(instruction, value)

        case notANewLineGOTO(goto, instructionIndex, instruction, index, value) =>
          //println(goto, instructionIndex, instruction, index, value)
          //println(instruction,value)
          instructions += ((instruction, index.toInt))

          populateNumbersAndNames(instruction, value)

        case newLine(lineNumber, instructionIndex, instruction, index, value) =>
          //println(lineNumber, instructionIndex, instruction, index, value)
          //println(instruction,value)
          instructions += instruction -> index.toInt

          populateNumbersAndNames(instruction, value)

        case notANewLine(instructionIndex, instruction, index, value) =>
          //println(instructionIndex, instruction, index, value)
          //println(instruction,value)
          instructions += ((instruction, index.toInt))

          populateNumbersAndNames(instruction, value)

        case onlyInstructionGOTO(goto, instructionIndex, instruction) =>
          //println(goto, instructionIndex, instruction)
          instructions += ((instruction, "None"))

        case onlyInstruction(instructionIndex, instruction) =>
          //println(instructionIndex, instruction)
          instructions += ((instruction, "None"))

        case _ => ()//println("Nothing")
      }
    }
    println("----")
    instructions.foreach(println)
    println("----")
    variableNames.foreach(println)
    println("----")
    variableValues.foreach(println)
    environment("instructions") = instructions
    environment("variableNames") = variableNames
    environment("variableValues") = variableValues
    environment
  }
}


object Run extends App {

  val pythonFileName = "if_conditional.py"
  val t = new Preprocessor()
  val assemblyCode = t.createAssemblyCode(pythonFileName)
  val whatToExecute = t.assemblyParser(assemblyCode)

}
