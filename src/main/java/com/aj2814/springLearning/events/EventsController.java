package com.aj2814.springLearning.events;

import com.aj2814.springLearning.github.GithubClient;
import com.aj2814.springLearning.github.RepositoryEvent;
import com.aj2814.springLearning.metrics.MetricsClient;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Controller
public class EventsController {
    private final GithubProjectRepository repository;
    private final GithubClient githubClient;
    private final MetricsClient metricsClient;

    public EventsController(GithubProjectRepository repository, GithubClient githubClient, MetricsClient metricsClient) {
        this.repository = repository;
        this.githubClient = githubClient;
        this.metricsClient = metricsClient;
    }

    @GetMapping("/events/{repoName}")
    @ResponseBody
    public RepositoryEvent[] fetchEvents(@PathVariable String repoName) {
        GithubProject project = this.repository.findByRepoName(repoName);
        return this.githubClient.fetchEvents(project.getOrgName(), project.getRepoName()).getBody();
    }

    @GetMapping("/")
    public String dashboard(Model model) {
        Iterable<GithubProject> projects = this.repository.findAll();
        List<DashboardEntry> entries = StreamSupport.stream(projects.spliterator(), true)
                .map(p -> new DashboardEntry(p, this.githubClient.fetchEventsList(p.getOrgName(), p.getRepoName())))
                .collect(Collectors.toList());
        model.addAttribute("entries", entries);
        model.addAttribute("rateLimitRemaining", this.metricsClient.fetchRateRemain());
        return "dashboard";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        model.addAttribute("projects", this.repository.findAll());
        return "admin";
    }
}
