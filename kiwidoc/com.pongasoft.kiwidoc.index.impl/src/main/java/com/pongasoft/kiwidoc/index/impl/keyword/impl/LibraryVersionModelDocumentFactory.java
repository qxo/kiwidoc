
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

package com.pongasoft.kiwidoc.index.impl.keyword.impl;

import com.pongasoft.kiwidoc.index.impl.ResourceEncoder;
import com.pongasoft.kiwidoc.model.LibraryVersionModel;
import com.pongasoft.util.core.annotations.ObjectInitializer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;

/**
 * @author yan@pongasoft.com
 */
public class LibraryVersionModelDocumentFactory extends AbstractDocumentFactory<LibraryVersionModel>
{
  /**
   * Constructor
   */
  @ObjectInitializer
  public LibraryVersionModelDocumentFactory()
  {
  }

  public LibraryVersionModelDocumentFactory(ResourceEncoder<String> resourceEncoder)
  {
    super(resourceEncoder);
  }

  /**
   * @param model the model to process
   * @return the document for the given model
   */
  public Document createDocument(LibraryVersionModel model)
  {
    Document doc = doCreateDocument(model);

    // library version to be able to unindex a library
    addFieldResource(doc, LIBRARY_VERSION_FIELD, model.getResource());

    // content
    doc.add(new Field(BODY_FIELD,
                      buildBody(model),
                      Field.Store.NO,
                      Field.Index.ANALYZED));
    return doc;
  }

  private String buildBody(LibraryVersionModel model)
  {
    StringBuilder sb = new StringBuilder();

    sb.append(model.getResource().toString());
    sb.append(FIELD_SEPARATOR);

    // overview
    if(model.getOverview() != null && model.getOverview().hasDoc())
    {
      sb.append(filterDoc(model.getOverview()));
      sb.append(FIELD_SEPARATOR);
    }

    return sb.toString();
  }
}