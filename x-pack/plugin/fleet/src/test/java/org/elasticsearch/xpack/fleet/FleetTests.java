/*
 * Copyright Elasticsearch B.V. and/or licensed to Elasticsearch B.V. under one
 * or more contributor license agreements. Licensed under the Elastic License
 * 2.0; you may not use this file except in compliance with the Elastic License
 * 2.0.
 */

package org.elasticsearch.xpack.fleet;

import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.indices.SystemIndexDescriptor;
import org.elasticsearch.test.ESTestCase;

import java.util.Collection;
import java.util.stream.Collectors;

import static org.hamcrest.Matchers.containsInAnyOrder;

public class FleetTests extends ESTestCase {

    public void testFleetIndexNames() {
        Fleet module = new Fleet();

        final Collection<SystemIndexDescriptor> fleetDescriptors = module.getSystemIndexDescriptors(Settings.EMPTY);

        assertThat(
            fleetDescriptors.stream().map(SystemIndexDescriptor::getIndexPattern).collect(Collectors.toList()),
            containsInAnyOrder(".fleet-servers*", ".fleet-policies*", ".fleet-agents*", ".fleet-actions*")
        );

        assertTrue(fleetDescriptors.stream().anyMatch(d -> d.matchesIndexPattern(".fleet-servers")));

        assertTrue(fleetDescriptors.stream().anyMatch(d -> d.matchesIndexPattern(".fleet-policies")));
        assertTrue(fleetDescriptors.stream().anyMatch(d -> d.matchesIndexPattern(".fleet-policies-leader")));

        assertTrue(fleetDescriptors.stream().anyMatch(d -> d.matchesIndexPattern(".fleet-agents")));

        assertTrue(fleetDescriptors.stream().anyMatch(d -> d.matchesIndexPattern(".fleet-actions")));
        assertTrue(fleetDescriptors.stream().anyMatch(d -> d.matchesIndexPattern(".fleet-actions-results")));

    }
}