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

import java.util.logging.Logger;
import java.io.Serializable;
import org.apache.geode.cache.client.ClientCache;

public class EmployeeKey implements Serializable {

  static final Logger logger = Logger.getAnonymousLogger();
  private String name;
  private int emplNumber;

  public EmployeeKey() {
  }

  public EmployeeKey(String n, int en) {
    this.name = n;
    this.emplNumber = en;
  }

  public String getName() {
    return(name);
  }

  public int getEmplNumber() {
    return(emplNumber);
  }

  public boolean equals(EmployeeKey key) {
    if ( this.name.equals(key.getName()) && 
        this.emplNumber==key.getEmplNumber() ) {
      return true;
    }
    return false;
  }

  public int hashCode() {
    int nameHashValue = this.name.hashCode();
    int numHashValue = this.emplNumber;
    logger.info("Key: name hash=" + nameHashValue + " num hash=" +
      numHashValue);
    return(nameHashValue + numHashValue);
  }

  public String toString() {
    return("Name: " + this.name + " Employee Number: " + this.emplNumber);
  }

}
