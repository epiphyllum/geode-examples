/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.geode.examples.partitioned;

import org.apache.geode.cache.client.ClientCache;

public class Producer extends BaseClient {

  public static void main(String[] args) {
    new Producer().populateRegion();
  }

  public Producer() {
  }

  public Producer(ClientCache clientCache) {
    this.clientCache = clientCache;
  }

  public void populateRegion() {

    EmployeeKey k1 = new EmployeeKey("Alex Able", 160);
    EmployeeData d1 = new EmployeeData(k1, 70000, 40);
    logger.info("About to try put on k1,d1 ");
    getRegion().put(k1, d1);

    EmployeeKey k2 = new EmployeeKey("Bertie Bell", 170);
    EmployeeData d2 = new EmployeeData(k2, 72000, 40);
    logger.info("About to try put on k2,d2 ");
    getRegion().put(k2, d2);

    EmployeeKey k3 = new EmployeeKey("Chris Call", 180);
    EmployeeData d3 = new EmployeeData(k3, 68000, 40);
    logger.info("About to try put on k3,d3 ");
    getRegion().put(k3, d3);

    logger.info("Inserted 3 entries.");
  }
}
