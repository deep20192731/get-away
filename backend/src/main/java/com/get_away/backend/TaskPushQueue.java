package com.get_away.backend;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;

/**
 * Created by rrk on 4/30/16.
 */
public class TaskPushQueue {

    private Queue processingQueue;
    private static TaskPushQueue instance = null;

    private TaskPushQueue(){
        processingQueue = QueueFactory.getDefaultQueue();
        System.out.println("Queue crated"+processingQueue.getQueueName());
    }

    public static TaskPushQueue getInstance(){
        if(instance == null)
            instance = new TaskPushQueue();
        return instance;
    }

    public void push(String data){
        processingQueue.add(TaskOptions.Builder.withPayload(new NotifPosterTask(data)));
    }
}
