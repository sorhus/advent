import java.nio.file.Paths
import java.util.concurrent.Executors

import cats.Show
import cats.effect.{ExitCode, IO, IOApp}
import fs2.{Pure, Sink, Stream, io}
import cats.implicits._

import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService}

abstract class App[O: Show] extends IOApp {

  private val ec: ExecutionContextExecutorService =
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

  def process(input: Stream[Pure, Byte]): Stream[Pure, O]

}