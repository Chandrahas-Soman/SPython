package BasicTests

import java.nio.file.{Files, Paths}

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Tag, TestSuite}
import org.scalatestplus.junit.JUnitRunner
import csoman.interpreter.Preprocessor

@RunWith(classOf[JUnitRunner])
class ArithmeticOperatorTests extends FunSuite with TestSuite {

  test("addition subtraction test with INTEGERS", Tag("ArithmeticTests")) {

    val testLines = List(
      "a = 3",
      "b = 5",
      "print(a + b)",
      "d = 3",
      "c = 5",
      "print(c - d)"
    )

    val path = "python/tests"
    val fileName = "add_sub_int.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)

    val t = new Preprocessor()
    val assemblyCode = t.createAssemblyCode(fileName)
    val whatToExecute = t.assemblyParser(assemblyCode)

    //assert()
  }

  test("addition subtraction test with FLOATS", Tag("ArithmeticTests")) {

    val testLines = List(
      "a = 3.21",
      "b = 5.093",
      "print(a + b)",
      "d = 3.0593",
      "c = 5.1222222",
      "print(c - d)"
    )

    val path = "python/tests"
    val fileName = "add_sub_float.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)

    val t = new Preprocessor()
    val assemblyCode = t.createAssemblyCode(fileName)
    val whatToExecute = t.assemblyParser(assemblyCode)

    //assert()
  }

  test("addition subtraction test with STRINGS", Tag("ArithmeticTests")) {

    val testLines = List(
      "a = 'This'",
      "b = 'is'",
      "d = 'Hard'",
      "c = 'Core!'",
      "print(a + b + c + d)"
    )

    val path = "python/tests"
    val fileName = "add_sub_string.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)

    val t = new Preprocessor()
    val assemblyCode = t.createAssemblyCode(fileName)
    val whatToExecute = t.assemblyParser(assemblyCode)

    //assert()
  }

}
