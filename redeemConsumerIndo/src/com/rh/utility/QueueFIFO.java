package com.rh.utility;

public class QueueFIFO {

	/*static BlockingQueue<String> queue = new LinkedBlockingQueue<String>();
	private static BlockingQueue<String> queueInstance = null;

	public static BlockingQueue<String> getStreamInstance() {

		if (queueInstance == null) {
			queueInstance = new LinkedBlockingQueue<String>();
		}
		return queueInstance;
	}

	public BlockingQueue<String> get() {
		return queue;
	}


	public void add(String value) {
		
		try {
			queue.put(value);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// Removes a single instance of the specified element from this collection
	public void remove(String value) {
		synchronized (queue) {
			queue.remove(value);
		}
	}

	public String[] getAll() {
//		System.out.println("Hi - 3");
		String[] data = queue.toArray(new String[5]);
		return data;
	}

	public String poll() {

		String data;
		try {
			data = queue.take();
			return data;
		} catch (InterruptedException e) {
			e.printStackTrace();
			return null;
		}
	}

	public void clear() {
		queue.clear();
	}

	// Returns true if this collection contains no elements
	public boolean isEmpty() {
		return queue.isEmpty();
	}
*/}
