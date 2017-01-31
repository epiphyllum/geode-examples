package org.apache.geode.examples.partitioned;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

/**
 * Created by kmiller on 1/30/17.
 */
public class EmployeeKeyTest {

  private EmployeeKey k;

  @Rule
  public ExpectedException expectedException = ExpectedException.none();

  @Before
  public void setup() {
    k = new EmployeeKey("First Last", 3001);
  }

  @Test
  public void testGetName() {
    assertEquals("First Last", k.getName());
  }

  @Test
  public void testGetEmplNumber() {
    assertEquals(3001, k.getEmplNumber());
  }

  @Test
  public void testEquals() {
    EmployeeKey otherKey = new EmployeeKey("First Last", 3001);
    assertTrue(k.equals(otherKey));
    EmployeeKey nonMatchingKey = new EmployeeKey("Othername", 1);
    assertFalse(k.equals(nonMatchingKey));
  }

  @Test
  public void testHashCode() {
    assertEquals(1773287935, k.hashCode());
  }

  @Test
  public void testToString() {
    assertEquals("Name: First Last Employee Number: 3001", k.toString());
  }
}