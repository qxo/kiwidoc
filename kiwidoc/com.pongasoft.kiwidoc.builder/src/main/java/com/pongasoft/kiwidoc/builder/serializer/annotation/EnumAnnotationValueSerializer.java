
/*
 * Copyright (c) 2012 Yan Pujante
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */

package com.pongasoft.kiwidoc.builder.serializer.annotation;

import com.pongasoft.kiwidoc.builder.serializer.Serializer;
import com.pongasoft.kiwidoc.builder.serializer.SerializerException;
import com.pongasoft.kiwidoc.model.annotation.EnumAnnotationValue;
import com.pongasoft.kiwidoc.model.type.GenericType;
import com.pongasoft.kiwidoc.model.type.Type;

import java.util.HashMap;
import java.util.Map;

import static com.pongasoft.kiwidoc.builder.serializer.SerializerUtils.putOnce;
import static com.pongasoft.kiwidoc.builder.serializer.SerializerUtils.req;

/**
 * @author yan@pongasoft.com
 */
public class EnumAnnotationValueSerializer implements Serializer<EnumAnnotationValue, Object>
{
  public static class FEnumAnnotationValue
  {
    public static final String type = "t";
    public static final String value = "v";
  }

  private final Serializer<Type, Object> _typeSerializer;

  public EnumAnnotationValueSerializer(Serializer<Type, Object> typeSerializer)
  {
    _typeSerializer = typeSerializer;
  }

  public Object serialize(EnumAnnotationValue annotation) throws SerializerException
  {
    if(annotation == null)
      return null;

    Map<String, Object> content = new HashMap<String, Object>();
    putOnce(content, FEnumAnnotationValue.type,
            _typeSerializer.serialize(annotation.getEnumType()));
    putOnce(content, FEnumAnnotationValue.value, annotation.getEnumValue());
    return content;
  }

  public EnumAnnotationValue deserialize(Object context, Object objectToDeserialize) throws SerializerException
  {
    if(objectToDeserialize == null)
      return null;

    @SuppressWarnings("unchecked")
    Map<String, Object> content = (Map<String, Object>) objectToDeserialize;

    return new EnumAnnotationValue((GenericType) _typeSerializer.deserialize(context,
                                                                             req(content, FEnumAnnotationValue.type)),
                                   (String) req(content, FEnumAnnotationValue.value));
  }
}