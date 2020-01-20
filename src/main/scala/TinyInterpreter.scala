package csoman.interpreter

import scala.collection.mutable.{ListBuffer, Map}

class TinyInterpreter(val dataStack: ListBuffer[String]) {

  //@TODO group instructions by functionality
  def LOAD_CONST() = ???
  def LOAD_NAME() = ???
  def STORE_NAME() = ???
  def BINARY_ADD() = ???
  def POP_TOP() = ???
  def RETURN_VALUE() = ???
  def SETUP_LOOP() = ???
  def COMPARE_OP() = ???
  def POP_JUMP_IF_FALSE() = ???
  def CALL_FUNCTION() = ???
  def INPLACE_ADD() = ???
  def JUMP_ABSOLUTE() = ???
  def POP_BLOCK() = ???
  def JUMP_FORWARD() = ???


  //TODO Loop through every instruction and use pattern matching
  def runCode(whatToExecute: Map[String, Any]) = ???

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