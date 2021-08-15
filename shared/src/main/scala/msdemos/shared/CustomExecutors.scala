package msdemos.shared

import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import scala.concurrent.ExecutionContext

object CustomExecutors {

  val ThreadCount = java.lang.Runtime.getRuntime().availableProcessors()

  /**
   * Best for IO bound tasks.
   * @param n Mu
   */
  def newFixedThreadPoolExecutor(n: Int = ThreadCount): ExecutorService = Executors.newFixedThreadPool(n)

  /**
   * Best for CPU bound tasks
   */
  def newForkJoinPool(): ExecutorService = new ForkJoinPool(ThreadCount)

  extension (executorService: ExecutorService) {
    def asScala: ExecutionContext = ExecutionContext.fromExecutorService(executorService)
  }
}
