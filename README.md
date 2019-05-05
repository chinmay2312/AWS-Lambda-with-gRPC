# Homework 6
### Description: you will gain experience with using gRPC and using REST requests to invoke lambda functions that you will deploy on AWS.
### Grade: 5% + bonus up to 3% for using only Scala and SBT to create a single SBT project that contains a protobuf library project and its dependent project that uses the protobufs to make gRPC calls to a lambda function.

## Preliminaries - skip those if you already read them in the previous homework descriptions.
Please set up your account with [AWS Educate](https://aws.amazon.com/education/awseducate/). Using your UIC email address will enable you to receive free credits for running your jobs in the cloud.

You will use logging and configuration management frameworks. You will comment your code extensively and supply logging statements at different logging levels (e.g., TRACE, INFO, ERROR) to record information at some salient points in the executions of your programs. All input and configuration variables must be supplied through configuration files -- hardcoding these values in the source code is prohibited and will be punished by taking a large percentage of points from your total grade! You are expected to use [Logback](https://logback.qos.ch/) and [SLFL4J](https://www.slf4j.org/) for logging and [Typesafe Conguration Library](https://github.com/lightbend/config) for managing configuration files. These and other libraries should be exported into your project using your script [build.sbt](https://www.scala-sbt.org/1.0/docs/Basic-Def-Examples.html). These libraries and frameworks are widely used in the industry, so learning them is the time well spent to improve your resume.

As before, when writing your program code in Scala, you should avoid using **var**s and while/for loops that iterate over collections using [induction variables](https://en.wikipedia.org/wiki/Induction_variable). Instead, you should learn to use collection methods **map**, **flatMap**, **foreach**, **filter** and many others with lambda functions, which make your code linear and easy to understand. Also, avoid mutable variables at all cost. Points will be deducted for having many **var**s and inductive variable loops without explanation why mutation is needed in your code - you can always do without it.

## Overview
In this homework, you will create and deploy your lambda functions on [AWS Lambda](https://aws.amazon.com/lambda/) and you will write a client program to invoke these lambda functions using [gRPC](https://grpc.io/) and independently a different client program to invoke the same lambda functions by using the [AWS API Gateway](https://aws.amazon.com/api-gateway/) with the requests GET and POST and DELETE.

Doing this homework enables students to put their theoretical knowledge about gRPC and REST architectural style on a firm footing in the context of creating and using lambda functions deployed in the cloud.


## Functionality
Your homework assignment consists of two interlocked parts: first, create a client program that uses gRPC to invoke a lambda calculator function deployed on AWS and second, to create a client program and the corresponding lambda function calculator(s) that use the REST methods (e.g., GET or POST) to interact. While you are free to determine how your lambda calculator works, there is a small subset of mandatory functionality that you must implement: the basic arithmetic functions +, -, /, and * and the extended functions Memory with the commands set and retrieve for storing and retrieving the last computed value.

The starting point is to follow the guide on [AWS Serverless Application Model (SAM)](https://docs.aws.amazon.com/serverless-application-model/latest/developerguide/serverless-quick-start.html). Once you follow the steps of the tutorial, you will be able to invoke a lambda function via the AWS API Gateway.

Next, you will learn how to create a [gRPC](https://grpc.io/) client program. I find this [tutorial on gRPC on HTTP/2](https://www.cncf.io/blog/2018/08/31/grpc-on-http-2-engineering-a-robust-high-performance-protocol/) very well written by Jean de Klerk, Developer Program Engineer at Google.

After that you will learn about [AWS API Gateway](https://docs.aws.amazon.com/apigateway/latest/developerguide/welcome.html) and determine how to use it to create RESTful API for your implementation of the calculator lambda function.

A guide to keep you on the right path is the [blog entry](https://blog.coinbase.com/grpc-to-aws-lambda-is-it-possible-4b29a9171d7f) that describes the process of using gRPC for invoking AWS lambda function in Go.

Excellent [guide how to create a REST service with AWS Lambda](https://blog.sourcerer.io/full-guide-to-developing-rest-apis-with-aws-api-gateway-and-aws-lambda-d254729d6992) includes instructions on how to set up and configure AWS Lambda.

## Baseline Submission
Your baseline project submission should include your implementation of lambda function calculators, a conceptual explanation in the document or in the comments in the source code of your design decisions, especially the state management part, and the documentation that describe the build and runtime process, to be considered for grading. Your project submission should include all your source code written in Scala as well as non-code artifacts (e.g., configuration files), your entire project should be buildable using the single build SBT.

## Submission deadline and logistics
Saturday, May 4 at 8PM CST via the bitbucket repository. Your submission will include the code for the protobuf and your client programs including the code generated with the compiler protoc, your documentation with instructions and detailed explanations on how to assemble and deploy your submission, and a document that explains how you designed and implemented the program, and what the limitations of your implementation are. Again, do not forget, please make sure that you will give both your TA and your instructor the read access to your private forked repository. Your name should be shown in your README.md file and other documents. Your code should compile and run from the command line using the commands ```sbt clean compile test``` and ```sbt clean compile run```. Also, you project should be IntelliJ friendly, i.e., your graders should be able to import your code into IntelliJ and run from there. Use .gitignore to exlude files that should not be pushed into the repo.

## Evaluation criteria
- the maximum grade for this homework is 5% with the bonus up to 3% for using only Scala and SBT to create a single SBT project that contains a protobuf library project and its dependent project that uses the protobufs to make gRPC calls to a lambda function. Points are subtracted from this maximum grade: for example, saying that 2% is lost if some requirement is not completed means that the resulting grade will be 5%-2% => 3%; if the core homework functionality does not work, no bonus points will be given;
- the code does not work in that it does not produce a correct output or crashes: up to 5% lost;
- having less than two unit tests that test the main functionality: up to 4% lost;
- missing comments and explanations from the program: up to 3% lost;
- logging is not used in the program: up to 3% lost;
- hardcoding the input values in the source code instead of using the suggested configuration libraries: up to 4% lost;
- no instructions in README.md on how to install and run your program: up to 4% lost;
- the documentation exists but it is insufficient to understand how you assembled and deployed all components of the cloud: up to 4% lost;
- the minimum grade for this homework cannot be less than zero.

That's it, folks!