import org.scalatest.FlatSpec
import spray.json._
import GRPCServer._
import com.typesafe.config.ConfigFactory

import scala.concurrent.ExecutionContext

/**
  * @author Chinmay Gangal
  */
class ClientTest extends FlatSpec{

  def calculate(a:Double, b:Double, op:String): Double = {
    op match {
      case "add" => a+b
      case "sub" => a-b
      case "mul" => a*b
      case "div" => {
        if(b==0)
          Double.NaN
        else
          a/b
      }
    }
  }

  /**
    * Unit test
    */
  "Lambda API" should "perform standard calculations" in {
    val a = 2
    val b = 4
    val op = "div"
    val responseAWS = scala.io.Source.fromURL("https://7ub4yveql2.execute-api.us-east-1.amazonaws.com/public/calc?operand1="+a+"&operand2="+b+"&operator="+op)
    val js = responseAWS.mkString.parseJson.asJsObject
    val ans = js.fields("c").toString()
    assert(calculate(a, b, op) == ans.toDouble)
  }

  /**
    * Integration test
    * This covers multiple Unit tests, inlcuding the following:
      * Checking gRPC implementation
      * calculator implementaion
      * server listeners
      * checking port consistency with server
      * Verifying port is read from config file, and not hardcoded
    */
  "GRPC Server" should "respond calculated result to GRPC Client" in {
    val server = new GRPCServer(ExecutionContext.global)
    startServer(server)
    //blockServerUntilShutdown(server)

    val client = GRPCClient("localhost", ConfigFactory.load().getInt("port"))
    val a = 2
    val b = 4
    val op = "div"
    val ans = client.greet(a+","+b+","+op)
    client.shutdown()
    stopServer(server)

    assert(ans.toDouble == calculate(a,b,op))

  }

}
