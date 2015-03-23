package edu.sjsu.cmpe273.lab2;

import io.grpc.ServerImpl;
import io.grpc.stub.StreamObserver;
import io.grpc.transport.netty.NettyServerBuilder;
import com.google.protobuf.Descriptors.Descriptor;	
import java.util.Random;

public class CreatePollServer {

	private int port = 50051;
	private ServerImpl server;


	private void start() throws Exception {
		server = NettyServerBuilder.forPort(port)
			.addService(PollServiceGrpc.bindService(new PollServiceImpl()))
			.build().start();
		Runtime.getRuntime().addShutdownHook(new Thread() {
		  @Override
		  public void run() {
			System.err.println("*** shutting down gRPC server since JVM is shutting down");
			CreatePollServer.this.stop();
			System.err.println("*** server shut down");
		  }
		});
	}
	
	 private void stop() {
    if (server != null) {
      server.shutdown();
    }
  }
  private static int generateID(){
	Random r = new Random();
	return r.nextInt(9000) + 1000; 	
  }

  public static void main(String[] args) throws Exception {
    final CreatePollServer server = new CreatePollServer();
    server.start();
  }
  
  private class PollServiceImpl implements PollServiceGrpc.PollService {
	
    @Override
     public void createPoll(edu.sjsu.cmpe273.lab2.PollRequest request,
        io.grpc.stub.StreamObserver<edu.sjsu.cmpe273.lab2.PollResponse> responseObserver) {
		String pollID = Integer.toString(generateID(),36);
		Descriptor descriptor = request.getDescriptor();
		String moderatorID = request.getField(descriptor.findFieldByNumber(1)).toString();
		System.out.println("Creating Poll for Moderator: " + moderatorID); 
		PollResponse response = PollResponse.newBuilder().setId(pollID).build();
		responseObserver.onValue(response);
		responseObserver.onCompleted();
    }
    private int generateID(){
        Random r = new Random();
        return r.nextInt(9000) + 1000;  
    } 
  }
}
