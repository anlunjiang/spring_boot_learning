package com.aj2814.springLearning.events;

import com.aj2814.springLearning.github.RepositoryEvent;

import java.util.List;

public class DashboardEntry{
    private final GithubProject project;
    private final List<RepositoryEvent> events;

    public DashboardEntry(GithubProject githubProject, List<RepositoryEvent> events) {
        this.project = githubProject;
        this.events = events;
    }

    public GithubProject getProject() {
        return project;
    }

    public List<RepositoryEvent> getEvents() {
        return events;
    }
}
