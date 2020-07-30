package io.eventuate.tram.events.publisher;


import io.eventuate.tram.events.common.DomainEvent;

import java.util.Arrays;
import java.util.List;

/**
 * 带有领域事件的返回结果
 * @param <T>
 */
public class ResultWithEvents<T> {
  /**
   * 返回结果
   */
  public final T result;
  /**
   * 领域事件列表
   */
  public final List<DomainEvent> events;

  public ResultWithEvents(T result, List<DomainEvent> events) {
    this.result = result;
    this.events = events;
  }

  public ResultWithEvents(T result, DomainEvent... events) {
    this.result = result;
    this.events = Arrays.asList(events);
  }
}
