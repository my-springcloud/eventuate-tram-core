package io.eventuate.tram.events.common;

public class EventMessageHeaders {
  // 领域对象的领域事件全限定类名
  public static final String EVENT_TYPE = "event-type";
  // 领域对象的全限定类名
  public static final String AGGREGATE_TYPE = "event-aggregate-type";
  // 领域对象的ID
  public static final String AGGREGATE_ID = "event-aggregate-id";
}
