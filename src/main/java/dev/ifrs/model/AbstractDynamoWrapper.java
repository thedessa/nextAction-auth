package dev.ifrs.model;

import com.amazonaws.services.dynamodbv2.document.Item;

public abstract class AbstractDynamoWrapper {

  public abstract Item toDynamoItem();
}
