import java.util.logging.Logger

import com.example.protos.hello.{GreeterGrpc, HelloReply, HelloRequest}
import com.typesafe.config.ConfigFactory
import io.grpc.{Server, ServerBuilder}

import scala.concurrent.{ExecutionContext, Future}
import spray.json._

/**
  * @author Chinmay Gangal
  */
object GRPCServer {
  private val logger = Logger.getLogger(classOf[GRPCServer].getName)

  def main(args: Array[String]): Unit = {
    val server: GRPCServer = new GRPCServer(ExecutionContext.global)
    startServer(server)
    blockServerUntilShutdown(server)
  }

  /**
    * Stateless method for starting server
    *
    * @param server GRPC server instance
    */
  def startServer(server: GRPCServer): Unit = {
    server.start()
  }

  /**
    * Stateless method for blocking server instance
    * @param server GRPC server instance
    */
  def blockServerUntilShutdown(server: GRPCServer): Unit =  {
    server.blockUntilShutdown()
  }

  def stopServer(server: GRPCServer): Unit =  {
    server.stop()
  }

  private val port:Int = ConfigFactory.load().getInt("port")
}

/**
  * @author Chinmay gangal
  * @param executionContext Context of execution
  */
class GRPCServer(executionContext: ExecutionContext) { self =>
  private[this] var server: Server = _

  private def start(): Unit = {
    server = ServerBuilder.forPort(GRPCServer.port).addService(GreeterGrpc.bindService(new GreeterImpl, executionContext)).build.start
    GRPCServer.logger.info("Server started, listening on " + GRPCServer.port)
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

      //Read input parameters
      val params = req.name.split(",")
      val a = params(0)
      val b = params(1)
      val op = params(2)

      //Call Lambda API Gateway
      val responseAWS = scala.io.Source.fromURL("https://7ub4yveql2.execute-api.us-east-1.amazonaws.com/public/calc?operand1="+a+"&operand2="+b+"&operator="+op)
      val result = responseAWS.mkString
      val json = result.parseJson.asJsObject
      //json.fields("c")
      //val reply = HelloReply(message = "Hello " + req.name + result)
      val reply = HelloReply(message = json.fields("c").toString())
      responseAWS.close()
      Future.successful(reply)
    }


  }

}