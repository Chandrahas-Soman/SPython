package BasicTests

import java.nio.file.{Files, Paths}

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Tag, TestSuite}
import org.scalatestplus.junit.JUnitRunner
import csoman.interpreter.Preprocessor

@RunWith(classOf[JUnitRunner])
class LogicalOperatorTests extends FunSuite with TestSuite {

  test("Logical Operator Tests with Booleans", Tag("LogicalOperatorTests")) {

    val testLines = List(
      "a = True",
      "b = False",
      "print(a & b)",
      "print(a and b)",
      "print(a or b)",
      "print(a | b)",
      "print(not a)"
    )

    val path = "python/tests"
    val fileName = "logical_operators_with_two_operand.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)

    val t = new Preprocessor()
    val assemblyCode = t.createAssemblyCode(fileName)
    val whatToExecute = t.assemblyParser(assemblyCode)

    //assert()
  }

  test("Logical Operator Tests with three boolean operands", Tag("LogicalOperatorTests")) {

    val testLines = List(
      "a = True",
      "b = False",
      "c = True",
      "print(a & b & c)",
      "print(a and b and c)",
      "print(a or b or c)",
      "print(a | b | c)",
      "print(not a)",
      "print(not a or not b or not c)",
      "print(a or b or c)",
      "print(a | b | c)",
    )

    val path = "python/tests"
    val fileName = "logical_operators_with_three_operand.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)

    val t = new Preprocessor()
    val assemblyCode = t.createAssemblyCode(fileName)
    val whatToExecute = t.assemblyParser(assemblyCode)

    //assert()
  }

  test("Logical Operator Test Exhaustive", Tag("LogicalOperatorTests")) {

    val testLines = List(
      "a = 'This'",
      "b = 'is'",
      "d = 'Hard'",
      "c = 'Core'",
      "print(a + b + c + d)"
    )

    val path = "python/tests"
    val fileName = "logical_operators_with_two_operand_boolean_exhaustive.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)

    val t = new Preprocessor()
    val assemblyCode = t.createAssemblyCode(fileName)
    val whatToExecute = t.assemblyParser(assemblyCode)

    //assert()
  }

}
