package com.liugeng.zhihudemo.async;

import java.util.List;

public interface EventHandler {
    void doHandle(EventModel event);
    List<EventType> getSupportEventTypes();
}
