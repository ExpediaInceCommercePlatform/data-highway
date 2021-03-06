/**
 * Copyright (C) 2016-2019 Expedia Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hotels.road.schema.validation;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

import java.util.Collection;
import java.util.Collections;

import org.apache.avro.Schema;
import org.apache.avro.Schema.Field;
import org.apache.avro.SchemaBuilder;
import org.junit.Test;

public class NonNullableUnionValidatingVisitorTest {
  private final NonNullableUnionValidatingVisitor underTest = new NonNullableUnionValidatingVisitor();
  private final Collection<String> breadcrumb = Collections.singleton("path");

  @Test
  public void notAUnion() throws Exception {
    Schema schema = SchemaBuilder.builder().intType();
    underTest.onVisit(schema, breadcrumb);
  }

  @Test(expected = IllegalArgumentException.class)
  public void unionNotSize2() throws Exception {
    Schema schema = SchemaBuilder.unionOf().intType().endUnion();
    underTest.onVisit(schema, breadcrumb);
  }

  @Test
  public void nullableUnion() throws Exception {
    Schema schema = SchemaBuilder.builder().nullable().intType();
    underTest.onVisit(schema, breadcrumb);
  }

  @Test
  public void optionalUnion() throws Exception {
    Schema schema = SchemaBuilder.unionOf().nullType().and().intType().endUnion();
    underTest.onVisit(schema, breadcrumb);
  }

  @Test(expected = IllegalArgumentException.class)
  public void nonNullableUnion() throws Exception {
    Schema schema = SchemaBuilder.unionOf().stringType().and().intType().endUnion();
    underTest.onVisit(schema, breadcrumb);
  }

  @Test
  public void field() {
    try {
      underTest.onVisit((Field) null, breadcrumb);
    } catch (Exception e) {
      fail();
    }
  }

  @Test
  public void result() {
    assertThat(underTest.getResult(), is(nullValue()));
  }
}
