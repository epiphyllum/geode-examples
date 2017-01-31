package org.apache.geode.examples.partitioned;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by kmiller on 1/31/17.
 */
public class EmployeeDataTest {
  private EmployeeData d;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setup() {
    EmployeeKey k = new EmployeeKey("First Last", 3001);
    d = new EmployeeData(k, 40000, 38);
  }

  @Test
  public void testGetNameAndNumber() {
    assertEquals("First Last", d.getNameAndNumber().getName());
    assertEquals(3001, d.getNameAndNumber().getEmplNumber());
  }

  @Test
  public void testGetSalary() {
    assertEquals(40000, d.getSalary());
  }

  @Test
  public void testGetHoursPerWeek() {
    assertEquals(38, d.getHoursPerWeek());
  }

  @Test
  public void testEquals() {
    EmployeeKey otherKey = new EmployeeKey("First Last", 3001);
    EmployeeData otherData = new EmployeeData(otherKey, 40000, 38);
    assertTrue(d.equals(otherData));
    EmployeeKey nonMatchingKey = new EmployeeKey("Othername", 1);
    EmployeeData nonMatchingData = new EmployeeData(nonMatchingKey, 39999, 40);
    assertFalse(d.equals(nonMatchingData));
  }

  @Test
  public void testToString() {
    assertEquals(d.getNameAndNumber().toString() + " salary=40000 hoursPerWeek=38", d.toString());
  }

}
