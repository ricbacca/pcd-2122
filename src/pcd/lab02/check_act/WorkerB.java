package pcd.lab02.check_act;

public class WorkerB extends Thread{
	
	private final Counter counter;
	private final int ntimes;
	
	public WorkerB(Counter c, int ntimes){
		counter = c;
		this.ntimes = ntimes;
	}
	
	public void run(){
		try {
			for (int i = 0; i < ntimes; i++){
				if (counter.getValue() < 1){
					counter.inc();
				}
			}
		} catch (Exception ex){
			ex.printStackTrace();
		}
	}
}
