package com.example.jsonpersistent;

import com.example.jsonpersistent.model.DataObject;

import java.util.List;

/**
 * Created by thomasliao on 2017/3/25.
 */

public interface DataObjectConverter<T> {
    T deserialize(List<DataObject> value);

    List<DataObject> serialize(T object);
}
