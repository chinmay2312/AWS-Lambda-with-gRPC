package com.example.protos.hello

object GreeterGrpc {
  val METHOD_SAY_HELLO: _root_.io.grpc.MethodDescriptor[com.example.protos.hello.HelloRequest, com.example.protos.hello.HelloReply] =
    _root_.io.grpc.MethodDescriptor.newBuilder()
      .setType(_root_.io.grpc.MethodDescriptor.MethodType.UNARY)
      .setFullMethodName(_root_.io.grpc.MethodDescriptor.generateFullMethodName("com.example.protos.Greeter", "SayHello"))
      .setSampledToLocalTracing(true)
      .setRequestMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[com.example.protos.hello.HelloRequest])
      .setResponseMarshaller(_root_.scalapb.grpc.Marshaller.forMessage[com.example.protos.hello.HelloReply])
      .build()
  
  val SERVICE: _root_.io.grpc.ServiceDescriptor =
    _root_.io.grpc.ServiceDescriptor.newBuilder("com.example.protos.Greeter")
      .setSchemaDescriptor(new _root_.scalapb.grpc.ConcreteProtoFileDescriptorSupplier(com.example.protos.hello.HelloProto.javaDescriptor))
      .addMethod(METHOD_SAY_HELLO)
      .build()
  
  trait Greeter extends _root_.scalapb.grpc.AbstractService {
    override def serviceCompanion = Greeter
    def sayHello(request: com.example.protos.hello.HelloRequest): scala.concurrent.Future[com.example.protos.hello.HelloReply]
  }
  
  object Greeter extends _root_.scalapb.grpc.ServiceCompanion[Greeter] {
    implicit def serviceCompanion: _root_.scalapb.grpc.ServiceCompanion[Greeter] = this
    def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.example.protos.hello.HelloProto.javaDescriptor.getServices().get(0)
  }
  
  trait GreeterBlockingClient {
    def serviceCompanion = Greeter
    def sayHello(request: com.example.protos.hello.HelloRequest): com.example.protos.hello.HelloReply
  }
  
  class GreeterBlockingStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterBlockingStub](channel, options) with GreeterBlockingClient {
    override def sayHello(request: com.example.protos.hello.HelloRequest): com.example.protos.hello.HelloReply = {
      _root_.scalapb.grpc.ClientCalls.blockingUnaryCall(channel, METHOD_SAY_HELLO, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterBlockingStub = new GreeterBlockingStub(channel, options)
  }
  
  class GreeterStub(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions = _root_.io.grpc.CallOptions.DEFAULT) extends _root_.io.grpc.stub.AbstractStub[GreeterStub](channel, options) with Greeter {
    override def sayHello(request: com.example.protos.hello.HelloRequest): scala.concurrent.Future[com.example.protos.hello.HelloReply] = {
      _root_.scalapb.grpc.ClientCalls.asyncUnaryCall(channel, METHOD_SAY_HELLO, options, request)
    }
    
    override def build(channel: _root_.io.grpc.Channel, options: _root_.io.grpc.CallOptions): GreeterStub = new GreeterStub(channel, options)
  }
  
  def bindService(serviceImpl: Greeter, executionContext: scala.concurrent.ExecutionContext): _root_.io.grpc.ServerServiceDefinition =
    _root_.io.grpc.ServerServiceDefinition.builder(SERVICE)
    .addMethod(
      METHOD_SAY_HELLO,
      _root_.io.grpc.stub.ServerCalls.asyncUnaryCall(new _root_.io.grpc.stub.ServerCalls.UnaryMethod[com.example.protos.hello.HelloRequest, com.example.protos.hello.HelloReply] {
        override def invoke(request: com.example.protos.hello.HelloRequest, observer: _root_.io.grpc.stub.StreamObserver[com.example.protos.hello.HelloReply]): Unit =
          serviceImpl.sayHello(request).onComplete(scalapb.grpc.Grpc.completeObserver(observer))(
            executionContext)
      }))
    .build()
  
  def blockingStub(channel: _root_.io.grpc.Channel): GreeterBlockingStub = new GreeterBlockingStub(channel)
  
  def stub(channel: _root_.io.grpc.Channel): GreeterStub = new GreeterStub(channel)
  
  def javaDescriptor: _root_.com.google.protobuf.Descriptors.ServiceDescriptor = com.example.protos.hello.HelloProto.javaDescriptor.getServices().get(0)
  
}