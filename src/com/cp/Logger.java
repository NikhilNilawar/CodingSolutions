package com.cp;

import java.util.Map;
import java.util.TreeMap;

public interface Logger {
    void start(int processId);
    void end(int processId);
    void poll();
}

class LoggerImplementation implements Logger {

    private TreeMap<Integer, Process> processMap;

    public LoggerImplementation() {
        processMap = new TreeMap<>();
    }

    @Override
    public void start(int processId) {
        processMap.put(processId, new Process(processId, System.currentTimeMillis()));
    }

    @Override
    public void end(int processId) {
        processMap.get(processId).setEndTime(System.currentTimeMillis());
    }

    @Override
    public void poll() {
        final Process process = processMap.firstEntry().getValue();
    }
}

class Process {
    private final int processId;
    private final long startTime;
    private long endTime;

    public Process(int processId, long startTime) {
        this.processId = processId;
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public int getProcessId() {
        return processId;
    }

    public long getStartTime() {
        return startTime;
    }
}
