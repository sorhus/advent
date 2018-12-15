import java.nio.file.Paths
import java.util.concurrent.Executors

import cats.Show
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import fs2.{Sink, Stream, io, text}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService}

abstract class IApp[O: Show] extends IOApp {

  val ec: ExecutionContextExecutorService =
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(2))
  private val bufferSize = 4096

  override def run(args: List[String]): IO[ExitCode] = {

    val input: Stream[IO, Byte] = args.headOption match {
      case Some(file) => io.file.readAll[IO](Paths.get(file), ec, bufferSize)
      case None => io.stdin[IO](bufferSize, ec)
    }

    input.through(process)
      .through(Sink.showLinesStdOut)
      .compile
      .drain
      .as(ExitCode.Success)
  }

  def toString(input: Stream[IO, Byte]): Stream[IO, String] = input.through(text.utf8Decode).through(text.lines)

  def toInt(input: Stream[IO, Byte]): Stream[IO, Int] = toString(input).map(_.toInt)

  def process(input: Stream[IO, Byte]): Stream[IO, O]

}