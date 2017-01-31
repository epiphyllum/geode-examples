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

public class Consumer extends BaseClient {

  public static void main(String[] args) {
    Consumer c = new Consumer();
    int numEntries = c.countEntriesOnServer();
    c.printRegionContents();
  }

  public Consumer() {}

  public Consumer(ClientCache clientCache) {
    this.clientCache = clientCache;
  }

  public int countEntriesOnServer() {
    int size = this.getRegion().keySetOnServer().size();
    logger.info(String.format("Done. %d entries available on the server(s).", size));
    return size;
  }

  public void printRegionContents() {
    Set<EmployeeKey> setOfKeys = this.getRegion().keySetOnServer();
    /*
     * for each key in setOfKeys print the entry
     */
    if (setOfKeys == null) {
      logger.info("setOfKeys is a null reference");
    }
    logger.info("Region contents:");
    for (EmployeeKey key : setOfKeys) {
      if (this.getRegion().getEntry(key) == null ) {
        logger.info("getEntry returned null");
      }
      logger.info(this.getRegion().getEntry(key).toString());
    }
  }

}
