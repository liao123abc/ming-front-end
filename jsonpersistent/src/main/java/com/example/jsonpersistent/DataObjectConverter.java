package com.example.jsonpersistent;

import com.example.jsonpersistent.model.DataObject;

/**
 * Created by thomasliao on 2017/3/25.
 */

public interface DataObjectConverter<T> {
    T deserialize(DataObject value);

    DataObject serialize(T queueObject);
}
