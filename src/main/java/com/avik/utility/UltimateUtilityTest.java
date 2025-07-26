package com.avik.utility.test;

import com.avik.utility.UltimateUtility;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UltimateUtilityTest {

    private UltimateUtility plugin;

    @BeforeEach
    public void setup() {
        // Here you would ideally mock plugin but for simplicity, just new instance (won't work fully without server)
        plugin = new UltimateUtility();

        // Mock admins list setup
        List<String> admins = new ArrayList<>();
        admins.add("TestAdmin1");
        admins.add("TestAdmin2");

        // Simulate config setting for admins (you'd need to mock getConfig() to return this in real tests)
        // plugin.getConfig().set("admins", admins); // Cannot run outside server environment without mocking
    }

    @Test
    public void testAdminListContains() {
        // Dummy test example for admins list contains check
        List<String> admins = new ArrayList<>();
        admins.add("TestAdmin1");
        admins.add("TestAdmin2");

        assertTrue(admins.contains("TestAdmin1"));
        assertFalse(admins.contains("NotAdmin"));
    }

    @Test
    public void testAddAdmin() {
        List<String> admins = new ArrayList<>();
        admins.add("TestAdmin1");

        // simulate adding admin
        admins.add("NewAdmin");

        assertEquals(2, admins.size());
        assertTrue(admins.contains("NewAdmin"));
    }
}