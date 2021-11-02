package msdemos.shared.analysis

import java.net.URL
import scala.deriving.*
import scala.compiletime.{summonAll}
import java.nio.file.Path

/**
 * Base function we use to convert case classes to CSV
 * @param a The object to convert
 */
def transform[A : Transformer](a: A) = summon[Transformer[A]].f(a)

trait Transformer[T]:
  def f(t: T): String

given Transformer[String] with
  def f(x: String) = x

given Transformer[Int] with
  def f(x: Int) = x.toString

given Transformer[Double] with
  def f(x: Double) = f"$x%.2f"

given Transformer[URL] with
  def f(x: URL) = x.toExternalForm

given Transformer[Path] with
  def f(x: Path) = x.getFileName.toString

given [T] (using t: Transformer[T]): Transformer[Option[T]] = 
  new Transformer[Option[T]]:
    def f(x: Option[T]) = x match
      case None => ""
      case Some(x) => t.f(x)

/**
 * Transforms a list of case classes into CSV data, including header row
 */
given [A <: Product] (using t: Transformer[A]): Transformer[List[A]] =
  new Transformer[List[A]]:
    def f(x: List[A]) = 
      val rows = asHeader(x.head) :: x.map(transform)
      rows.mkString("\n")

/**
 * Turns a case class into a CSV row string
 * 
 * Fucking voodoo from https://kavedaa.github.io/auto-ui-generation/auto-ui-generation.html
 */
inline given [A <: Product] (using m: Mirror.ProductOf[A]): Transformer[A] =
  new Transformer[A]:
    type ElemTransformers = Tuple.Map[m.MirroredElemTypes, Transformer]
    val elemTransformers = summonAll[ElemTransformers].toList.asInstanceOf[List[Transformer[Any]]]  
    def f(a: A): String = 
      val elems = a.productIterator.toList
      val transformed = elems.zip(elemTransformers) map { (elem, transformer) => transformer.f(elem) }
      transformed.mkString(",")


/**
 * Turns a case class into a CSV header row string
 */
def asHeader[A <: Product](a: A): String = a.productElementNames.toList.mkString(",")
  