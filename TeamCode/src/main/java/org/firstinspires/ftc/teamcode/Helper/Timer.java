package org.firstinspires.ftc.teamcode.Helper;

public class Timer {

    public Timer(){
        reset();
    }

    private long lastMS;

    public long getCurrentMS()
    {
        return System.nanoTime() / 1000000L;
    }

    public long getLastMS()
    {
        return this.lastMS;
    }

    public boolean hasReached(long milliseconds)
    {
        return getCurrentMS() - this.lastMS >= milliseconds;
    }

    public void reset()
    {
        this.lastMS = getCurrentMS();
    }

    public double formattedTime(){
       return (int)(getCurrentMS() - lastMS) / 1000.0;
    }

}