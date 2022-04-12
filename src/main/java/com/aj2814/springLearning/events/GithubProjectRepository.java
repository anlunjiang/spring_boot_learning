package com.aj2814.springLearning.events;

import org.springframework.data.repository.PagingAndSortingRepository;

public interface GithubProjectRepository extends PagingAndSortingRepository<GithubProject, Long> {
    GithubProject findByRepoName(String repoName);

}
