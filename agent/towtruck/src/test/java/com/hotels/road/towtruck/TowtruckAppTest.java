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
package com.hotels.road.towtruck;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneOffset;

import org.junit.Test;

public class TowtruckAppTest {

  private final TowtruckApp underTest = new TowtruckApp();

  @Test
  public void keySupplier() {
    Clock clock = Clock.fixed(Instant.ofEpochMilli(1493905649163L), ZoneOffset.UTC);

    String result = underTest.keySupplier(clock, "keyPrefix").get();

    assertThat(result, is("keyPrefix/2017-05-04/20170504T134729Z.json.gz"));
  }
}
