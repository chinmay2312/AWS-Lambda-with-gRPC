import java.util.concurrent.TimeUnit
import java.util.logging.{Level, Logger}

import com.example.protos.hello.{GreeterGrpc, HelloRequest}
import com.example.protos.hello.GreeterGrpc.GreeterBlockingStub
import io.grpc.{ManagedChannel, ManagedChannelBuilder, StatusRuntimeException}

object GRPCClient {
  def apply(host: String, port: Int): GRPCClient = {
    val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext().build
    val blockingStub = GreeterGrpc.blockingStub(channel)
    new GRPCClient(channel, blockingStub)
  }

  def main(args: Array[String]): Unit = {
    val client = GRPCClient("localhost", 50051)
    try {
      val user = args.headOption.getOrElse("1,5,div")
      client.greet(user)
    } finally {
      client.shutdown()
    }
  }
}

class GRPCClient private(
                                private val channel: ManagedChannel,
                                private val blockingStub: GreeterBlockingStub
                              ) {
  private[this] val logger = Logger.getLogger(classOf[GRPCClient].getName)

  def shutdown(): Unit = {
    channel.shutdown.awaitTermination(5, TimeUnit.SECONDS)
  }

  /** Say hello to server. */
  def greet(name: String): Unit = {
    val params = name.split(",")
    logger.info("Calculating " + params(0)+" "+params(2)+" "+ params(1)+ " from AWS Lambda function through API Gateway")
    val request = HelloRequest(name = name)
    try {
      val response = blockingStub.sayHello(request)
      logger.info("Result: " + response.message)
    }
    catch {
      case e: StatusRuntimeException =>
        logger.log(Level.WARNING, "RPC failed: {0}", e.getStatus)
    }
  }
}