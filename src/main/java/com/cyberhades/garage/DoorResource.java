package com.cyberhades.garage;

import javax.annotation.PreDestroy;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.cyberhades.garage.model.DoorResponse;
import com.pi4j.io.gpio.GpioController;
import com.pi4j.io.gpio.GpioFactory;
import com.pi4j.io.gpio.GpioPinDigitalInput;
import com.pi4j.io.gpio.GpioPinDigitalOutput;
import com.pi4j.io.gpio.PinPullResistance;
import com.pi4j.io.gpio.PinState;
import com.pi4j.io.gpio.RaspiPin;
import com.sun.jersey.spi.resource.Singleton;

@Singleton
@Path("door")
public class DoorResource {

	// RaspiPin.GPIO_03 ==> BCM GPIO 22;
	// RaspiPin.GPIO_04 ==> BCM GPIO 23;
	// RaspiPin.GPIO_00 ==> BCM GPIO 17;
	// RaspiPin.GPIO_01 ==> BCM GPIO 18;

	private final GpioController gpio = GpioFactory.getInstance();

	private final GpioPinDigitalInput RIGHT_DOOR_SENSOR = gpio.provisionDigitalInputPin(RaspiPin.GPIO_03,
			"Right Door Sensor", PinPullResistance.PULL_UP);
	private final GpioPinDigitalInput LEFT_DOOR_SENSOR = gpio.provisionDigitalInputPin(RaspiPin.GPIO_04,
			"Left Door Sensor", PinPullResistance.PULL_UP);

	private final GpioPinDigitalOutput RIGHT_DOOR_RELAY = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_00,
			"Left Door Relay", PinState.HIGH);
	private final GpioPinDigitalOutput LEFT_DOOR_RELAY = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01,
			"Left Door Relay", PinState.HIGH);

	@GET
	@Path("right/position")
	@Produces(MediaType.APPLICATION_JSON)
	public DoorResponse getRightDoorPosition() {
		DoorResponse doorResponse = new DoorResponse();
		doorResponse.setDoor("right");
		doorResponse.setStatusCode(0);
		doorResponse.setStatusMessage(RIGHT_DOOR_SENSOR.isHigh() ? "Open" : "Closed");
		return doorResponse;
	}

	@GET
	@Path("left/position")
	@Produces(MediaType.APPLICATION_JSON)
	public DoorResponse getLeftDoorPosition() {
		DoorResponse doorResponse = new DoorResponse();
		doorResponse.setDoor("left");
		doorResponse.setStatusCode(0);
		doorResponse.setStatusMessage(LEFT_DOOR_SENSOR.isHigh() ? "Open" : "Closed");
		return doorResponse;
	}

	@GET
	@Path("right/push")
	@Produces(MediaType.APPLICATION_JSON)
	public DoorResponse getRightDoorPush() {
		DoorResponse doorResponse = new DoorResponse();
		doorResponse.setDoor("right");
		try {
			RIGHT_DOOR_RELAY.low();
			Thread.sleep(500);
			RIGHT_DOOR_RELAY.high();
			doorResponse.setStatusCode(0);
			doorResponse.setStatusMessage("OK");
		} catch (InterruptedException e) {
			doorResponse.setStatusCode(-1);
			doorResponse.setStatusMessage("FAILED");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doorResponse;
	}

	@GET
	@Path("left/push")
	@Produces(MediaType.APPLICATION_JSON)
	public DoorResponse getLeftDoorPush() {
		DoorResponse doorResponse = new DoorResponse();
		doorResponse.setDoor("left");
		try {
			LEFT_DOOR_RELAY.low();
			Thread.sleep(500);
			LEFT_DOOR_RELAY.high();
			doorResponse.setStatusCode(0);
			doorResponse.setStatusMessage("OK");
		} catch (InterruptedException e) {
			doorResponse.setStatusCode(-1);
			doorResponse.setStatusMessage("FAILED");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return doorResponse;
	}

	@PreDestroy
	private void shutdown() {
		gpio.shutdown();
	}

}
