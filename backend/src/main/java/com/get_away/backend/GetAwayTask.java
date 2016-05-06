package com.get_away.backend;

import com.google.appengine.api.taskqueue.DeferredTask;

/**
 * Created by rrk on 4/30/16.
 */
public interface GetAwayTask extends DeferredTask  {
        public void execute();
}