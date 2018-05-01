public class Semaphore{
		private int value;
	
		public Semaphore() {
			value = 1;
		}
		
		public Semaphore(int value) {
			this.value = value;
		}
		public synchronized void acquire() {
			while (value == 0)
				try {
						wait();
				} catch (InterruptedException ie) { }
			value--;
		}
		 public synchronized void release() {
			 ++value;
			 notify();
		}
}
