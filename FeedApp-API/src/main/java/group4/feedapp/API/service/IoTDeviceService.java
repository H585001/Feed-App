package group4.feedapp.API.service;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import group4.feedapp.API.dao.IoTDeviceDAO;
import group4.feedapp.API.dao.IoTVotesDAO;
import group4.feedapp.API.dao.PollDAO;
import group4.feedapp.API.model.IoTDevice;
import group4.feedapp.API.model.Poll;

@Service
public class IoTDeviceService {
	
	private IoTDeviceDAO iotDao;
	private PollDAO pollDAO;
	
	@Autowired
	public IoTDeviceService(IoTDeviceDAO iotDao, PollDAO pollDAO) {
		this.iotDao = iotDao;
		this.pollDAO = pollDAO;
	}
	
	public IoTDevice connectDeviceToPoll(Long id, Poll poll) {
		IoTDevice device = iotDao.readIoTDevice(id);
		device.setLinkedPoll(poll);
		device = iotDao.updateIoTDevice(id, device);
		poll.getLinkedDevices().add(device);
		pollDAO.updatePoll(poll.getId(), poll);
		return device;
	}
	
	public Poll getConnectedPoll(Long id) {
		return iotDao.readIoTDevice(id).getLinkedPoll();
	}
	
	public Collection<IoTDevice> getAllDevices() {
		return iotDao.readIoTDevices();
	}

	public IoTDevice getDevice(Long id) {
		return iotDao.readIoTDevice(id);
	}

	public IoTDevice addDevice(String deviceName) {
		return iotDao.createIoTDevice(deviceName, null);
	}
	
	public IoTDevice deleteDevice(Long id) {
		return iotDao.deleteIoTDevice(id);
	}

}
