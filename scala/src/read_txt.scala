import scala.io.Source

object read_txt {
  def main(args: Array[String]): Unit = {
    val source = Source.fromFile("src/main/resources/古诗2.txt","UTF-8")
    val lines = source.getLines()
    lines.foreach(println)

  }
}
