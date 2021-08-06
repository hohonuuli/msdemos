package msdemos.shared

import java.util.concurrent.Executors
import java.util.concurrent.ForkJoinPool
import java.util.concurrent.Executor
import java.util.concurrent.ExecutorService
import scala.concurrent.ExecutionContext

object CustomExecutors {

  val ThreadCount = java.lang.Runtime.getRuntime().availableProcessors()

  def newFixedThreadPoolExecutor(): ExecutorService = Executors.newFixedThreadPool(ThreadCount)

  def newForkJoinPool(): ExecutorService = new ForkJoinPool(ThreadCount)

  extension (executorService: ExecutorService) {
    def asScala() = ExecutionContext.fromExecutorService(executorService)
  }
}
