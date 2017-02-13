/*
 * Licensed to the Apache Software Foundation (ASF) under one or more contributor license
 * agreements. See the NOTICE file distributed with this work for additional information regarding
 * copyright ownership. The ASF licenses this file to You under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance with the License. You may obtain a
 * copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.geode.examples.partitioned;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

import java.util.Set;

import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;

public class ProducerTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private Producer producer;
  private ClientCache clientCache = mock(ClientCache.class);
  private Region<EmployeeKey, EmployeeData> region1 = mock(Region.class);
  private Region<BadEmployeeKey, EmployeeData> region2 = mock(Region.class);
  private ClientRegionFactory clientRegionFactory = mock(ClientRegionFactory.class);
  private Set keys = mock(Set.class);


  @Before
  public void setup() {
    when(region1.getName()).thenReturn(Producer.REGION1_NAME);
    when(region2.getName()).thenReturn(Producer.REGION2_NAME);
    when(keys.size()).thenReturn(Producer.NUM_ENTRIES);
    when(clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY))
        .thenReturn(clientRegionFactory);
    when(clientRegionFactory.create(Consumer.REGION1_NAME)).thenReturn(region1);
    when(clientRegionFactory.create(Consumer.REGION2_NAME)).thenReturn(region2);

  }


  @Test
  public void testPopulateRegion() {
    producer = new Producer(clientCache);
    producer.populateRegion();
    verify(region1, times(10)).put(any(), any());

  }

  @Test
  public void testPopulateBadRegion() {
    producer = new Producer(clientCache);
    producer.populateBadRegion();
    verify(region2, times(10)).put(any(), any());

  }

  @After
  public void tearDown() {

  }
}
