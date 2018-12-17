package common

import java.nio.file.Paths
import java.util.concurrent.Executors

import cats.Show
import cats.effect.{ExitCode, IO, IOApp}
import cats.implicits._
import fs2.{Pure, Sink, Stream, io, text}

import scala.concurrent.{ExecutionContext, ExecutionContextExecutorService}

abstract class App[O: Show] extends IOApp {

  val ec: ExecutionContextExecutorService =
    ExecutionContext.fromExecutorService(Executors.newFixedThreadPool(2))
  private val bufferSize = 4096

  override def run(args: List[String]): IO[ExitCode] = {

    val input: Stream[IO, Byte] = args.headOption match {
      case Some(file) => io.file.readAll[IO](Paths.get(file), ec, bufferSize)
      case None =>
        val input = s"/${getClass.getName.split("\\.").head}.txt"
        io.readInputStream[IO](IO(getClass.getResourceAsStream(input)), bufferSize, ec)
    }

    input.through(process)
      .through(Sink.showLinesStdOut)
      .compile
      .drain
      .as(ExitCode.Success)
  }

  def toString(input: Stream[Pure, Byte]): Stream[Pure, String] = input.through(text.utf8Decode).through(text.lines)

  def toInt(input: Stream[Pure, Byte]): Stream[Pure, Int] = toString(input).map(_.toInt)

  def process(input: Stream[Pure, Byte]): Stream[Pure, O]

}