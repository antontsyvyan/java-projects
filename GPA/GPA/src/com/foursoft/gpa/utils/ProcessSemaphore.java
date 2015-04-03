package com.foursoft.gpa.utils;

import java.util.concurrent.Semaphore;

public  class ProcessSemaphore {
	
	public static Semaphore femutuSemaphore=new Semaphore(1);
	public static Semaphore femutiSemaphore=new Semaphore(1);
	public static Semaphore femimpSemaphore=new Semaphore(1);
	public static Semaphore feminbSemaphore=new Semaphore(1);
	public static Semaphore femtraSemaphore=new Semaphore(1);

}
