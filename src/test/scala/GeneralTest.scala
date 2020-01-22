import java.nio.file.{Files, Paths}

import org.junit.runner.RunWith
import org.scalatest.{FunSuite, Tag, TestSuite}
import org.scalatestplus.junit.JUnitRunner


// Boolean, None, assignment failure
// remaining --> Referencing, Other Data Types, like List, Set, Dictionaries, Mutability / Immutability, ..

/*

  exhaustive testing is not possible
  therefore, testing a general case and corner cases!
  max_val, min_val, 0
 */

@RunWith(classOf[JUnitRunner])
class GeneralTest extends FunSuite with TestSuite {

  test("assignment operator test", Tag("Compiler")) {

    val testLines = List(
      "a = 3",
      "b = 'b'",
      "c = 0",
      "d = -23",
      "e = -0.6",
      "f = 'd'",
      "g = 3.2",
      "h = ''",
      "i = 'This is a String'"
    )

    val path = "intermediateFiles/python/"
    val fileName = "assignment_operator.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)


  }

  test("if conditional test", Tag("Compiler")) {

    val testLines = List(
      "a = 3",
      "b = 5",
      "c = 9202",
      "if a < b:",
      "\tprint(a + b)",
      "else:",
      "\tprint(c)"
    )

    val path = "intermediateFiles/python/"
    val fileName = "if_conditional.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)
  }

  test("while loop test", Tag("Compiler")) {

    val testLines = List(
      "a = 3",
      "b = 5",
      "c = 9202",
      "while a < b:",
      "\tprint(a + b)",
      "\ta += 1"
    )

    val path = "intermediateFiles/python/"
    val fileName = "while_loop.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)

  }

  test("arithmatic", Tag("Compiler")) {

    val testLines = List(
      "a = 3",
      "b = 5",
      "print(a + b)"
    )

    val path = "intermediateFiles/python/"
    val fileName = "arithmatic.py"

    val content = testLines.mkString("\n").getBytes
    Files.write(Paths.get(path + fileName), content)

  }

}
