package PartI;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.PriorityQueue;
import java.util.Queue;

public class Packet {
	
	public Packet(Byte[] payload, int priority) {
		// figure out how this works
	}
	
	public static void main(String[] args) {
		PriorityQueue pq = new PriorityQueue();
				
		for (int i=0;i<10;i++) {
			Byte[] payload = new Byte[256];
			int priority = (int)Math.random()*5 + 1;
			Packet p = new Packet(payload, priority);
		}
		
		while (!pq.isEmpty()) {
			System.out.println("got packet " + pq.remove());
		}
	}

}
