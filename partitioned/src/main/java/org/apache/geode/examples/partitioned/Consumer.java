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
    c.printRegionContents();
  }

  public Consumer() {}

  public Consumer(ClientCache clientCache) {
    this.clientCache = clientCache;
  }


  public void printRegionContents() {
    /* Print EmployeeRegion size and contents */
    Region r1 = this.getRegion1();
    Set<EmployeeKey> setOfKeys1 = r1.keySetOnServer();
    logger.info(setOfKeys1.size() + " entries in EmployeeRegion on the server(s).");
    if (setOfKeys1.size() > 0) {
      logger.info("Contents of EmployeeRegion:");
      for (EmployeeKey key : setOfKeys1) {
        logger.info(r1.get(key).toString());
      }
    }

    /* Print BadEmployeeRegion size and contents */
    Region r2 = this.getRegion2();
    Set<EmployeeKey> setOfKeys2 = r2.keySetOnServer();
    logger.info(setOfKeys2.size() + " entries in BadEmployeeRegion on the server(s).");
    if (setOfKeys2.size() > 0) {
      logger.info("Contents of BadEmployeeRegion:");
      for (EmployeeKey key : setOfKeys2) {
        logger.info(r2.get(key).toString());
      }
    }
  }

}
