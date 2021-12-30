package dev.ifrs.dao;

import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.ItemCollection;
import com.amazonaws.services.dynamodbv2.document.PrimaryKey;
import com.amazonaws.services.dynamodbv2.document.QueryOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import com.amazonaws.services.dynamodbv2.document.spec.PutItemSpec;
import com.amazonaws.services.dynamodbv2.document.spec.QuerySpec;
import com.amazonaws.services.dynamodbv2.document.spec.UpdateItemSpec;
import com.amazonaws.services.dynamodbv2.document.utils.NameMap;
import com.amazonaws.services.dynamodbv2.document.utils.ValueMap;
import com.amazonaws.services.dynamodbv2.model.ConditionalCheckFailedException;
import com.amazonaws.services.dynamodbv2.model.ReturnValue;

import org.apache.commons.lang3.tuple.Pair;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

import javax.inject.Inject;

import dev.ifrs.model.AbstractDynamoWrapper;

public abstract class AbstractModelDao<M extends AbstractDynamoWrapper> {

  public String tableName;
  public Class<M> modelClass;
  public DynamoDB dynamo = null;
  public Table table = null;

  @Inject
  public ModelDaoProvider modelDaoProvider;

  public AbstractModelDao() {
  }

  public AbstractModelDao(final Class<M> modelClass,
                          final String tableName) {
    this.modelClass = modelClass;
    this.tableName = tableName;
  }

  public DynamoDB getDynamo() {
    if (this.dynamo == null) {
      this.dynamo = new DynamoDB(this.modelDaoProvider.getDynamoClient());
    }
    return this.dynamo;
  }

  public Table getTable() {
    if (this.table == null) {
      this.table = this.getDynamo().getTable(this.tableName);
    }
    return this.table;
  }

  public Item get(final PrimaryKey primaryKey) {
    return this.getTable().getItem(primaryKey);
  }
}
