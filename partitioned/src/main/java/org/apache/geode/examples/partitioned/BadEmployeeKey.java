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

import java.util.Objects;
import java.util.logging.Logger;
import java.io.Serializable;
import org.apache.geode.cache.client.ClientCache;

public class BadEmployeeKey implements Serializable {

  private static final long serialVersionUID = 1L;

  static final Logger logger = Logger.getAnonymousLogger();
  private String name;
  private int emplNumber;

  public BadEmployeeKey() {}

  public BadEmployeeKey(String n, int en) {
    this.name = n;
    this.emplNumber = en;
  }

  public String getName() {
    return (name);
  }

  public int getEmplNumber() {
    return (emplNumber);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    BadEmployeeKey that = (BadEmployeeKey) o;

    if (emplNumber != that.emplNumber) {
      return false;
    }
    return name.equals(that.name);
  }

  /*
   * This hashCode is what make this class a very poor implementation. It always returns the value
   * 1, so that every entry gets placed in the same bucket, and partitioning is useless.
   *
   * Forgetting to define, or implementing an erroneous hashCode can result in rotten distribution
   * of region entries across buckets (and therefore, across partitions).
   */
  @Override
  public int hashCode() {
    return 1;
  }

  public String toString() {
    return ("Name: " + this.name + " Employee Number: " + this.emplNumber);
  }

}
