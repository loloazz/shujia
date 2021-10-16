class Dog(la:String) extends Speak(la) with TailWagger with Speaker  {
  override def startTail(): Unit = println("tail is  wagging")

  override def stopTail(): Unit = println("tail is stop")

  override def speak() = println("wang！wang！")

}

object dogTest extends App{
  var dog= new Dog("akaka")
  dog.startTail:Unit
  dog.stopTail()
  dog.speak()
  dog.speak2()
}