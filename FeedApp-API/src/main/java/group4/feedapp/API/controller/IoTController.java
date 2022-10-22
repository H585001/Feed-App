package group4.feedapp.API.controller;
import java.util.Collection;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import group4.feedapp.API.model.IoTDevice;
import group4.feedapp.API.model.IoTVotes;
import group4.feedapp.API.model.Poll;
import group4.feedapp.API.service.IoTDeviceService;
import group4.feedapp.API.service.PollService;

@RestController
public class IoTController {
	
	private final IoTDeviceService deviceService;
	private final PollService pollService;
	
	public IoTController(IoTDeviceService deviceService, PollService pollService) {
		this.deviceService = deviceService;
		this.pollService = pollService;
	}
	
	@PutMapping("/iot/{id}")
	public ResponseEntity<IoTDevice> connectDeviceToPoll(@PathVariable Long id, @RequestBody Long pollId) {
		Poll poll = pollService.getPoll(pollId);
		IoTDevice device = deviceService.getDevice(id);
		if(poll == null || device == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		// TODO Add response if device is not connected
		return new ResponseEntity<>(deviceService.connectDeviceToPoll(id, poll), HttpStatus.OK);
	}
	
	@GetMapping("/iot")
	public ResponseEntity<Collection<IoTDevice>> getAllDevices() {
		return new ResponseEntity<>(deviceService.getAllDevices(), HttpStatus.OK);
	}
	
	@PostMapping("/iot")
	public ResponseEntity<IoTDevice> createDevice(@RequestBody String deviceName) {
		IoTDevice device = deviceService.addDevice(deviceName);
		if(device == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(device, HttpStatus.OK);
	}
	
	@GetMapping("/iot/{id}")
	public ResponseEntity<IoTDevice> getDevice(@PathVariable Long id) {
		IoTDevice device = deviceService.getDevice(id);
		if(device == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(device, HttpStatus.OK);
	}
	
	@GetMapping("/iot/{id}/poll")
	public ResponseEntity<Poll> getConnectedPoll(@PathVariable Long id) {
		IoTDevice device = deviceService.getDevice(id);
		if(device == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(device.getLinkedPoll(), HttpStatus.OK);
	}
	
	@PostMapping("/iot/{id}/vote")
	public ResponseEntity<Poll> iotVoteOnPoll(@PathVariable Long id, @RequestBody IoTVotes votes) {
		IoTDevice device = deviceService.getDevice(id);
		if(device == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		if(device.getLinkedPoll() == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		
		votes = pollService.voteOnPoll(device.getLinkedPoll().getId(), id, votes);
		if(votes == null) {
			return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
		}
		return new ResponseEntity<>(votes.getIotVotePoll(), HttpStatus.OK);
	}
	
	@DeleteMapping("/iot/{id}")
	public ResponseEntity<IoTDevice> deleteDevice(@PathVariable Long id) {
		IoTDevice device = deviceService.deleteDevice(id);
		if(device == null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(device, HttpStatus.OK);
	}
	
}
