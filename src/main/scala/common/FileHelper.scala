package common

object FileHelper {
  /**
   * Loan Pattern
   * wrapping a block of code in try catch finally
   *
   * @param resource
   * @param f transforms input type A to output type B
   * @tparam A a resource that has a close() method
   * @tparam B output type
   * @return
   */
  def using[A <: { def close(): Unit }, B](resource: A)(f: A => B): B =
    try {
      f(resource)
    } finally {
      resource.close()
    }
}
