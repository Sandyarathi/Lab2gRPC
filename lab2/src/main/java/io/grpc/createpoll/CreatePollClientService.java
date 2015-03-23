package edu.sjsu.cmpe273.lab2;

import io.grpc.ChannelImpl;
import io.grpc.transport.netty.NegotiationType;
import io.grpc.transport.netty.NettyChannelBuilder;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import edu.sjsu.cmpe273.lab2.Poll;
import edu.sjsu.cmpe273.lab2.PollID;
import com.google.protobuf.Descriptors;


public class CreatePollClientService {

private final ChannelImpl channel;

private final PollServiceGrpc.PollServiceBlockingStub blockingStub;

	public CreatePollClientService(String host, int port) {
		channel = NettyChannelBuilder.forAddress(host, port).negotiationType(NegotiationType.PLAINTEXT).build();
		blockingStub = PollServiceGrpc.newBlockingStub(channel);
	}

	public static PollID run(edu.sjsu.cmpe273.lab2.Poll poll, String moderatorId) throws InterruptedException{
		CreatePollClientService client = new CreatePollClientService("localhost",50051);
		PollResponse response=null;
		PollID createdPoll = new PollID();
		try{
			response = client.sendMessage(poll, moderatorId);
			Descriptors.Descriptor descriptor = response.getDescriptor();
			createdPoll.id = response.getField(descriptor.findFieldByNumber(1)).toString();
			System.out.println("Created Poll with ID: " + createdPoll.id);
		}
		finally{
			client.shutdown();
		}
		return createdPoll;
	}

	public PollResponse sendMessage(edu.sjsu.cmpe273.lab2.Poll poll, String moderatorId){
		System.out.println("Choice length: " + poll.choice.length);
		if (poll.choice == null || poll.choice.length < 2) {
           		throw new RuntimeException("choice must have two items in it");
        	}	
		PollRequest request = PollRequest.newBuilder().setModeratorId(moderatorId).setQuestion(poll.question).setStartedAt(poll.started_at).setExpiredAt(poll.expired_at).addAllChoice(Arrays.asList(poll.choice)).build();
		PollResponse response = blockingStub.createPoll(request);
		return response;
	}

	public void shutdown() throws InterruptedException {
   		 channel.shutdown().awaitTerminated(5, TimeUnit.SECONDS);
  	}

}
