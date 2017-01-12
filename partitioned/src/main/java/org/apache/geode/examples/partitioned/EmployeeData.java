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

public class EmployeeData {

  static final Logger logger = Logger.getAnonymousLogger();
  private EmployeeKey nameAndNumber;
  private int salary;
  private int hoursPerWeek;

  public EmployeeData() {
    this.nameAndNumber = new EmployeeKey("no name", 0);
    this.salary = 0;
    this.hoursPerWeek = 0;
  }

  public EmployeeData(EmployeeKey k, int s, int hrs) {
    this.nameAndNumber = k;
    this.salary = s;
    this.hoursPerWeek = hrs;
  }

  public EmployeeKey getEmployeeKey() {
    return(nameAndNumber);
  }

  public int getSalary() {
    return(salary);
  }

  public int getHoursPerWeek() {
    return(hoursPerWeek);
  }

  public String toString() {
    return(nameAndNumber.toString() + " salary=" + this.salary +
     " hoursPerWeek=" + this.hoursPerWeek);
  }

}
