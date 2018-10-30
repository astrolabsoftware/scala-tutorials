/**
  * I am the object toto! Objects can contain class, routines or variables.
  * Functions cannot be isolated. They have to be in objects or class methods.
  */
object toto {

  /**
    * Entry point of our application. Arguments (if any) are passed
    * as an array of String, and the user have to specify their true type later.
    *
    */
  def main(args: Array[String]) : Unit = {
    val myInstance = new toto()
    printVal(myInstance.name)
  }

  /**
    * I am a standard routine.
    */
  def printVal(str: String) : Unit = {
    println(str)
  }

  /**
    * I am a class! It is common to name the class as the object.
    * 
    */
  class toto {

    // Attributes
    val name : String = "toto"
    val age : Double = 9

    /** Method which does nothing */
    def doNothing : Unit = {
      // You could have done something as well...
    }
  }
}
