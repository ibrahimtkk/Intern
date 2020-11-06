package com.veniture.util;

import com.atlassian.sal.api.net.RequestFactory;
import model.pojo.TempoPlanner.Allocation;
import org.slf4j.Logger;

import java.util.List;

public class PersonalAvailabilityTimes {
    private Logger logger;
    private RequestFactory requestFactory;

    public PersonalAvailabilityTimes(Logger logger, RequestFactory requestFactory) {
        this.logger = logger;
        this.requestFactory = requestFactory;
    }

    public List<Allocation> invoke() throws Exception {
        List<Allocation> allocations;

        RemoteSearcher remoteSearcher = new RemoteSearcher(requestFactory);
        allocations = remoteSearcher.getAllocationsByDate();
        return allocations;
    }
}
