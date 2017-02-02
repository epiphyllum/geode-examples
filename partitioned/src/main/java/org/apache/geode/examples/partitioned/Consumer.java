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

import java.util.*;
import org.apache.geode.cache.client.ClientCache;
import org.apache.geode.cache.Region;

public class Consumer extends BaseClient {

  public static void main(String[] args) {
    Consumer c = new Consumer();
    int numEntries = c.countEntriesOnServer();
    logger.info(String.format("%d entries in the region on the server(s).", numEntries));
    c.printRegionContents();
  }

  public Consumer() {}

  public Consumer(ClientCache clientCache) {
    this.clientCache = clientCache;
  }

  public int countEntriesOnServer() {
    int size = this.getRegion().keySetOnServer().size();
    return size;
  }

  public void printRegionContents() {
    Region myRegion = this.getRegion();
    Set<EmployeeKey> setOfKeys = myRegion.keySetOnServer();
    /*
     * for each key in setOfKeys print the entry
     */
    logger.info("Region contents:");
    for (EmployeeKey key : setOfKeys) {
      logger.info(myRegion.get(key).toString());
    }
  }

}
