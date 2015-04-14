package com.maozy.test;

import java.util.ArrayList;
import java.util.List;

/**
 * N个任务，平均分配给M个线程处理
 * @author maozy
 *
 */
public class NTaskPerThread {
	
	int task_num = 12;
	int thread_num = 3;
	
	List<Task> taskList = new ArrayList<NTaskPerThread.Task>();
	long totalTime = 0; //任务运行时间，用于比较不同线程数量的效率
	
	public NTaskPerThread() {
		
	}
	
	public static void main(String[] args) {
		NTaskPerThread perThead = new NTaskPerThread();
		perThead.test();
	}
	
	
	public void test() {
		for (int i = 0; i < task_num; i++){
			taskList.add(new Task(i));
		}
		
		//给每个线程分配任务,应list从索引0开始,所以分配任务编号从0开始  
		int num = task_num / thread_num;//这样子可能还有余数,应该把余数也分摊  
		if(task_num % thread_num != 0) {
			num ++;//如果有余数(一定小于thread_num),则前面的线程分摊下,每个线程多做一个任务
		}
		
		for (int i = 0; i < thread_num; i++) {
			int start = i * num;
			int end = Math.min((i + 1) * 4, taskList.size());//最后一个线程任务可能不够  
			
			new TaskThread(start, end).start();
		}
		
	}
	
	
	
	
	public class Task{
		private int n;
		
		public Task(int n) {
			this.n = n;
		}
		
		public void run() {
			System.out.println("run task number : " + n);
			for (int i = 0; i < 1000000000; i++) {
				int s = 0;
				s += i;
			}
		}
	}
	
	public class TaskThread extends Thread{
		int start;
		int end;
		
		public TaskThread(int start, int end){
			this.start = start;
			this.end = end;
		}
		
		public void run() {
			long beginTime = System.currentTimeMillis();
			
			for (; start < end; start++) {
				taskList.get(start).run();
			}
			long stopTime = System.currentTimeMillis();
			totalTime += (stopTime - beginTime);
			System.out.println("=====take " + totalTime + " 毫秒=====");
		}
	}
}
