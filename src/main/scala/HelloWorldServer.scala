import java.util.logging.Logger

import com.example.protos.hello.{GreeterGrpc, HelloReply, HelloRequest}

import io.grpc.{Server, ServerBuilder}
import scala.concurrent.{ExecutionContext, Future}

//import scalaj.http._

object HelloWorldServer {
  private val logger = Logger.getLogger(classOf[HelloWorldServer].getName)

  def main(args: Array[String]): Unit = {
    val server = new HelloWorldServer(ExecutionContext.global)
    server.start()
    server.blockUntilShutdown()
  }

  private val port = 50051
}

class HelloWorldServer(executionContext: ExecutionContext) { self =>
  private[this] var server: Server = _

  private def start(): Unit = {
    server = ServerBuilder.forPort(HelloWorldServer.port).addService(GreeterGrpc.bindService(new GreeterImpl, executionContext)).build.start
    HelloWorldServer.logger.info("Server started, listening on " + HelloWorldServer.port)
    sys.addShutdownHook {
      System.err.println("*** shutting down gRPC server since JVM is shutting down")
      self.stop()
      System.err.println("*** server shut down")
    }
  }

  private def stop(): Unit = {
    if (server != null) {
      server.shutdown()
    }
  }

  private def blockUntilShutdown(): Unit = {
    if (server != null) {
      server.awaitTermination()
    }
  }

  private class GreeterImpl extends GreeterGrpc.Greeter {
    override def sayHello(req: HelloRequest): Future[HelloReply] = {

      //val responseAWS = Http("https://7ub4yveql2.execute-api.us-east-1.amazonaws.com/public/calc?operand1=2&operand2=3&operator=add")//.asString
      //val responseAWS = "{\"a\":2,\"b\":3,\"op\":\"add\",\"c\":5}"
      val params = req.name.split(",")
      val a = params(0)
      val b = params(1)
      val op = params(2)
      val responseAWS = scala.io.Source.fromURL("https://7ub4yveql2.execute-api.us-east-1.amazonaws.com/public/calc?operand1="+a+"&operand2="+b+"&operator="+op)
      val result = responseAWS.mkString
      //val reply = HelloReply(message = "Hello " + req.name + result)
      val reply = HelloReply(message = "Result = "+result)
      responseAWS.close()
      Future.successful(reply)
    }


  }

}