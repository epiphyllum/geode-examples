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

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.geode.cache.client.ClientRegionFactory;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import org.apache.geode.cache.Region;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.client.NoAvailableLocatorsException;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

public class ConsumerTest {

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  private Consumer consumer;
  private ClientCache clientCache = mock(ClientCache.class);
  private Region region1 = mock(Region.class);
  private Region region2 = mock(Region.class);
  private ClientRegionFactory clientRegionFactory = mock(ClientRegionFactory.class);
  private Set keys = mock(Set.class);

  @Before
  public void setup() {
    when(region1.getName()).thenReturn(Consumer.REGION1_NAME);
    when(region2.getName()).thenReturn(Consumer.REGION2_NAME);
    when(keys.size()).thenReturn(Consumer.NUM_ENTRIES);
    // when(region1.keySetOnServer()).thenReturn(keys);
    // when(region2.keySetOnServer()).thenReturn(keys);
    when(clientCache.createClientRegionFactory(ClientRegionShortcut.PROXY))
        .thenReturn(clientRegionFactory);
    when(clientRegionFactory.create(Consumer.REGION1_NAME)).thenReturn(region1);
    when(clientRegionFactory.create(Consumer.REGION2_NAME)).thenReturn(region2);

    /* Make a small map that will provide values as a region would */
    Map<EmployeeKey, EmployeeData> emplMap = new HashMap<>();
    EmployeeKey k1 = new EmployeeKey("Bertie Bell", 170);
    EmployeeData d1 = new EmployeeData(k1, 72000, 40);
    emplMap.put(k1, d1);
    EmployeeKey k2 = new EmployeeKey("Toni Tiptoe", 180);
    EmployeeData d2 = new EmployeeData(k2, 70000, 40);
    emplMap.put(k2, d2);
    /* Mock the region keySetOnServer,size, and get methods with values from the map */
    when(region1.keySetOnServer()).thenReturn(emplMap.keySet());
    when(region1.size()).thenReturn(emplMap.size());
    when(region1.get(eq(k1))).thenReturn(emplMap.get(d1));
    when(region1.get(eq(k2))).thenReturn(emplMap.get(d2));

    Map<BadEmployeeKey, EmployeeData> badEmplMap = new HashMap<>();
    BadEmployeeKey bk1 = new BadEmployeeKey("Bertie Bell", 170);
    EmployeeData bd1 = new EmployeeData(bk1, 72000, 40);
    badEmplMap.put(bk1, bd1);
    BadEmployeeKey bk2 = new BadEmployeeKey("Toni Tiptoe", 180);
    EmployeeData bd2 = new EmployeeData(bk2, 70000, 40);
    badEmplMap.put(bk2, bd2);
    /* Mock the region keySetOnServer,size, and get methods with values from the map */
    when(region2.keySetOnServer()).thenReturn(badEmplMap.keySet());
    when(region2.size()).thenReturn(badEmplMap.size());
    when(region2.get(eq(bk1))).thenReturn(bd1);
    when(region2.get(eq(bk2))).thenReturn(bd2);

    // when(region1.get(any())).then(new Answer() {
    //
    // @Override
    // public Object answer(InvocationOnMock invocation) throws Throwable {
    // EmployeeKey key = invocation.getArgumentAt(0, EmployeeKey.class);
    // return emplMap.get(key);
    // }
    // });

    consumer = new Consumer(clientCache);
  }

  @Test
  public void numberOfEntriesShouldBeGreaterThanZero() throws Exception {
    assertTrue(consumer.NUM_ENTRIES > 0);
  }

  @Test
  public void testConsumerGetRegion1() {
    assertEquals("Region names do not match", Consumer.REGION1_NAME,
        consumer.getRegion1().getName());
  }

  /*
   * Doesn't work because mocked clientRegionFactory.create(any()) returns region1, not region2
   */
  @Test
  public void testConsumerGetRegion2() {
    assertEquals("Region names do not match", Consumer.REGION2_NAME,
        consumer.getRegion2().getName());
  }

  @Test
  public void testPrintRegionContents() {
    consumer.printRegionContents();
  }

}
