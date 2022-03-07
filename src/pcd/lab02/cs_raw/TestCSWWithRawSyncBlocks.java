package pcd.lab02.cs_raw;

public class TestCSWWithRawSyncBlocks {

	public static void main(String[] args) {
		Object lock = new Object();
		new MyWorkerB("MyAgent-01",lock).start();
		new MyWorkerA("MyAgent-02",lock).start();		
	}

}
