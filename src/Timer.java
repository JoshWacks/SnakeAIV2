public class Timer {
    long startTime;
    long endTime;


    public void start(){
        startTime=System.currentTimeMillis();

    }

    public void stop(){
        endTime=System.currentTimeMillis();

    }

    public long getTime(){
        return endTime-startTime;
    }

}
