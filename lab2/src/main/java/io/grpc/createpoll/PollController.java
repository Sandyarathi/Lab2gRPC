package edu.sjsu.cmpe273.lab2;

import java.util.ArrayList;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.grpc.ChannelImpl;
import io.grpc.transport.netty.NegotiationType;
import io.grpc.transport.netty.NettyChannelBuilder;

import java.util.concurrent.TimeUnit;

import edu.sjsu.cmpe273.lab2.Poll;


@RestController
public class PollController {

@RequestMapping(value="/moderators/{moderator_id}/polls", method = RequestMethod.POST, produces = "application/json")
	public ResponseEntity<PollID> createPoll(@PathVariable int moderator_id, @RequestBody Poll poll)throws Exception {
		PollID response = CreatePollClientService.run(poll,String.valueOf(moderator_id));
		return new ResponseEntity<PollID>(response,HttpStatus.CREATED);
		  
	}

}
